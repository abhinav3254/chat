package com.multi.service;


import com.multi.entity.Book;
import com.multi.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;

    public ResponseEntity<String> addMovie(Book book) {
        try {
            book.setDate(new Date());
            bookRepo.save(book);
            return new ResponseEntity<>("book saved",HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("something went wrong", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Page<Book>> getAllBooks(int page, int size) {
        try {
            // Create a Pageable object to represent the requested page
            Pageable pageable = PageRequest.of(page, size);

            // Retrieve a page of books using the repository and the Pageable object
            Page<Book> bookPage = bookRepo.findAll(pageable);

            // Return the page of books as a ResponseEntity
            return new ResponseEntity<>(bookPage, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
