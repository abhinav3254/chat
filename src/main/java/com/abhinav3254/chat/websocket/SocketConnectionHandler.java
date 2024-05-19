package com.abhinav3254.chat.websocket;

import com.abhinav3254.chat.dto.MessageDTO;
import com.abhinav3254.chat.jwt.JwtUtils;
import com.abhinav3254.chat.model.Message;
import com.abhinav3254.chat.model.User;
import com.abhinav3254.chat.repository.MessageRepository;
import com.abhinav3254.chat.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SocketConnectionHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final Map<String, WebSocketSession> userSessionMap = new ConcurrentHashMap<>();

    public SocketConnectionHandler(JwtUtils jwtUtils, UserRepository userRepository, MessageRepository messageRepository) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("connected :- " + session.getId());
        webSocketSessions.add(session);

        // Extract user ID from token and map to session
        HttpHeaders httpHeaders = session.getHandshakeHeaders();
        String token = Objects.requireNonNull(httpHeaders.get("authorization")).get(0).substring(7);
        Claims claims = jwtUtils.extractAllClaims(token);
        String userId = claims.get("id").toString();
        userSessionMap.put(userId, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println("disconnected :- " + session.getId());
        webSocketSessions.remove(session);

        // Remove user session from map
        userSessionMap.values().remove(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
        String payload = message.getPayload().toString();
        MessageDTO messageDTO = objectMapper.readValue(payload, MessageDTO.class);

        // Extract sender ID from token
        HttpHeaders httpHeaders = session.getHandshakeHeaders();
        String token = Objects.requireNonNull(httpHeaders.get("authorization")).get(0).substring(7);
        Claims claims = jwtUtils.extractAllClaims(token);
        String senderId = claims.get("id").toString();
        messageDTO.setSender(senderId);

        // Save message to a database
        Optional<User> senderUserOptional = userRepository.findById(Long.parseLong(messageDTO.getSender()));
        Optional<User> receiverUserOptional = userRepository.findById(Long.parseLong(messageDTO.getReceiver()));

        if (senderUserOptional.isPresent() && receiverUserOptional.isPresent()) {
            User senderUser = senderUserOptional.get();
            User receiverUser = receiverUserOptional.get();

            Message messageDatabase = new Message();
            messageDatabase.setSender(senderUser);
            messageDatabase.setReceiver(receiverUser);
            messageDatabase.setMessage(messageDTO.getContent());
            messageDatabase.setSendDate(new Date());

            messageRepository.save(messageDatabase);

            // Send message to the recipient
            WebSocketSession recipientSession = userSessionMap.get(messageDTO.getReceiver());
            if (recipientSession != null && recipientSession.isOpen()) {
                recipientSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(messageDTO)));
            }
        }
    }
}
