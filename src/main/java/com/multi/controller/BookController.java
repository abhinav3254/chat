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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateBookById(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id,book);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<Page<Book>> findBookByAuthor(@PathVariable String author,
                                                       @RequestParam(defaultValue = "0") Integer page,
                                                       @RequestParam(defaultValue = "10") Integer size
                                                       ) {
        return bookService.findBookByAuthor(author,page,size);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<Book>> findBookByCategory(@PathVariable String category,
                                                         @RequestParam(defaultValue = "0") Integer page,
                                                         @RequestParam(defaultValue = "10") Integer size
                                                               ) {
        return bookService.findBookByCategory(category,page,size);
    }

    @PutMapping("/add/all")
    public ResponseEntity<String> addMultipleBooks(@RequestBody List<Book> list) {
        return bookService.addMultipleBooks(list);
    }

    @GetMapping("/search/{search}")
    public ResponseEntity<Page<Book>> searchBooks(@PathVariable String search,
                                                  @RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer size) {
        return bookService.searchBooks(search,page,size);
    }

}
