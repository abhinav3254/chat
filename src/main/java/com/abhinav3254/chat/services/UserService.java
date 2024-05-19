package com.abhinav3254.chat.services;


import com.abhinav3254.chat.dto.ChatHistoryDTO;
import com.abhinav3254.chat.dto.LoginDTO;
import com.abhinav3254.chat.jwt.JwtUtils;
import com.abhinav3254.chat.model.Message;
import com.abhinav3254.chat.model.User;
import com.abhinav3254.chat.repository.MessageRepository;
import com.abhinav3254.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MessageRepository messageRepository;

    public ResponseEntity<?> registerUser(User user) {
        if (validateUser(user)) {
            user.setRole("USER");
            return ResponseEntity.ok(userRepository.save(user));
        }
        return ResponseEntity.status(500).body("Can't register");
    }

    private boolean validateUser(User user) {
        if (user.getEmail().isEmpty()) return false;
        if (user.getPassword().isEmpty()) return false;
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) return false;
        return true;
    }

    public ResponseEntity<?> login(LoginDTO loginDTO) {
        Optional<User> userOptional = userRepository.findByEmail(loginDTO.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(loginDTO.getPassword())) {
                String token = jwtUtils.generateToken(user.getEmail(), user.getId(), user.getRole());
                return ResponseEntity.ok(token);
            }
            return ResponseEntity.status(403).body("Wrong Password");
        } return ResponseEntity.status(404).body("User not found");
    }


    public ResponseEntity<?> getAllTheUsers() {
        return ResponseEntity.ok(this.userRepository.findAll());
    }

    public ResponseEntity<?> getChatHistory(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) return ResponseEntity.status(404).body("user chat not found");
        else {
            List<Message> messageList = messageRepository.findByReceiver(userOptional.get());
            List<ChatHistoryDTO> history = new ArrayList<>();
            for(Message message:messageList) {
                ChatHistoryDTO chatHistoryDTO = new ChatHistoryDTO();
                chatHistoryDTO.setMessage(message.getMessage());
                chatHistoryDTO.setReceiver(message.getReceiver().getId().toString());
                history.add(chatHistoryDTO);
            }
            return ResponseEntity.ok(history);
        }
    }
}
