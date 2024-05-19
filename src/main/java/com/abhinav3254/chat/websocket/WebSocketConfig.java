package com.abhinav3254.chat.websocket;

import com.abhinav3254.chat.jwt.JwtUtils;
import com.abhinav3254.chat.repository.MessageRepository;
import com.abhinav3254.chat.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public WebSocketConfig(JwtUtils jwtUtils,UserRepository userRepository,MessageRepository messageRepository) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @Bean
    public SocketConnectionHandler socketConnectionHandler() {
        return new SocketConnectionHandler(jwtUtils,userRepository,messageRepository);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketConnectionHandler(), "/chat")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setAllowedOrigins("*");
    }
}
