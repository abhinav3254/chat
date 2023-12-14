package com.multi.service;


import com.multi.entity.Book;
import com.multi.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Service class for managing books.
 */
@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepo bookRepo;


    /**
     * Adds a new book to the repository.
     *
     * @param book The book to be added.
     * @return ResponseEntity indicating the status of the operation.
     */
    public ResponseEntity<String> addMovie(Book book) {
        try {
            book.setDate(new Date());
            bookRepo.save(book);
            return new ResponseEntity<>("book saved",HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("Error occurred while adding a book", e);
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves a paginated list of all books.
     *
     * @param page The requested page number.
     * @param size The number of books per page.
     * @return ResponseEntity containing the paginated list of books.
     */
    public ResponseEntity<Page<Book>> getAllBooks(int page, int size) {
        try {
            // Create a Pageable object to represent the requested page
            Pageable pageable = PageRequest.of(page, size);

            // Retrieve a page of books using the repository and the Pageable object
            Page<Book> bookPage = bookRepo.findAll(pageable);

            // Return the page of books as a ResponseEntity
            return new ResponseEntity<>(bookPage, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("Error occurred while retrieving books", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
