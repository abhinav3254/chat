package com.multi.controller;

import com.multi.entity.Book;
import com.multi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PutMapping("/add")
    public ResponseEntity<String> addMovie(@RequestBody Book book) {
        return bookService.addMovie(book);
    }


    @GetMapping("/")
    public ResponseEntity<Page<Book>> getAllBooks(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return bookService.getAllBooks(page, size);
    }

}
