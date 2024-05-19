package com.abhinav3254.chat.services;


import com.abhinav3254.chat.dto.LoginDTO;
import com.abhinav3254.chat.jwt.JwtUtils;
import com.abhinav3254.chat.model.User;
import com.abhinav3254.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

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
            String token = jwtUtils.generateToken(user.getEmail(), user.getId(), user.getRole());
            return ResponseEntity.ok(token);
        } return ResponseEntity.status(404).body("User not found");
    }


}
