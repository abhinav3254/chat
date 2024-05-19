package com.abhinav3254.chat.controller;


import com.abhinav3254.chat.dto.LoginDTO;
import com.abhinav3254.chat.model.User;
import com.abhinav3254.chat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAllTheUsers() {
        return userService.getAllTheUsers();
    }

    @GetMapping("/chat-history/{userId}")
    public ResponseEntity<?> getChatHistory(@PathVariable("userId") Long userId) {
        return userService.getChatHistory(userId);
    }

}
