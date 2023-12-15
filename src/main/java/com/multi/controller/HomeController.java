package com.multi.controller;


import com.multi.entity.FileEntity;
import com.multi.repo.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@RestController
public class HomeController {

    @Autowired
    private FileRepo fileRepository;

//    @PutMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile multipartFile) {
//        if (multipartFile.isEmpty())
//        return new ResponseEntity<>("file failed to upload", HttpStatus.NOT_ACCEPTABLE);
//        else return new ResponseEntity<>("file uploaded", HttpStatus.ACCEPTED);
//    }

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



    @PutMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return new ResponseEntity<>("File failed to upload", HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            // Convert MultipartFile to byte array
            byte[] fileBytes = multipartFile.getBytes();

            // Create FileEntity and set the file content
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(multipartFile.getOriginalFilename());
            fileEntity.setFileContent(fileBytes);


            fileRepository.save(fileEntity);

            return new ResponseEntity<>("File uploaded", HttpStatus.ACCEPTED);
        } catch (IOException e) {
            return new ResponseEntity<>("Error uploading file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {

        System.out.println("------>> Here is the issue <<---------");

        // Retrieve FileEntity from the database
        Optional<FileEntity> optionalFileEntity = fileRepository.findById(fileId);

        if (optionalFileEntity.isPresent()) {
            FileEntity fileEntity = optionalFileEntity.get();

            // Set headers to indicate file download
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", fileEntity.getFileName());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            // Return the file content as the response
            return new ResponseEntity<>(fileEntity.getFileContent(), headers, HttpStatus.OK);
        } else {
            // File not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
