package com.multi.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@RestController
public class HomeController {

    @PutMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile multipartFile) {
        if (multipartFile.isEmpty())
        return new ResponseEntity<>("file failed to upload", HttpStatus.NOT_ACCEPTABLE);
        else return new ResponseEntity<>("file uploaded", HttpStatus.ACCEPTED);
    }

    @GetMapping("/readFile")
    public ResponseEntity<String> readFile(@RequestParam String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder content = new StringBuilder();
            String line;
            content.append("File content:\n");
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return new ResponseEntity<>(content.toString(),HttpStatus.ACCEPTED);
        } catch (IOException e) {
            return new ResponseEntity<>("An error occurred while processing the file: " + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
