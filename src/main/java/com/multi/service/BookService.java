package com.multi.service;


import com.multi.entity.Book;
import com.multi.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Service class for managing books.
 *
 * @author AbKumar
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
            return new ResponseEntity<>(bookPage, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while retrieving books", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a book with the given ID.
     *
     * @param id The ID of the book to be deleted.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    public ResponseEntity<String> deleteBook(Long id) {
        try {
            Optional<Book> optionalBook = bookRepo.findById(id);
            if (optionalBook.isPresent()) {
                bookRepo.delete(optionalBook.get());
                return new ResponseEntity<>("book deleted successfully",HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("book not found",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("error while deleting the book :- "+e);
            return new ResponseEntity<>("error while deleting the user",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Updates a book with the given ID using the information from the provided new book.
     *
     * @param id      The ID of the book to be updated.
     * @param bookNew The new book information for the update.
     * @return ResponseEntity indicating the result of the update operation.
     */
    public ResponseEntity<String> updateBook(Long id,Book bookNew) {
        try {
            Optional<Book> optionalBook = bookRepo.findById(id);
            if (optionalBook.isPresent()) {
                Book book = replaceBook(optionalBook.get(),bookNew);
                bookRepo.save(book);
                return new ResponseEntity<>("book updated successfully",HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("book not found by id",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error occurred while updating a book", e);
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Replaces the information of an old book with the information from a new book.
     *
     * @param oldBook The existing book to be updated.
     * @param newBook The new book information for the update.
     * @return The updated book.
     */
    private Book replaceBook(Book oldBook, Book newBook) {

        if (newBook.getTitle() != null) {
            oldBook.setTitle(newBook.getTitle());
        }
        if (newBook.getIsbn() != null) {
            oldBook.setIsbn(newBook.getIsbn());
        }
        if (newBook.getPageCount() != null) {
            oldBook.setPageCount(newBook.getPageCount());
        }
        if (newBook.getThumbnailUrl() != null) {
            oldBook.setThumbnailUrl(newBook.getThumbnailUrl());
        }
        if (newBook.getShortDescription() != null) {
            oldBook.setShortDescription(newBook.getShortDescription());
        }
        if (newBook.getLongDescription() != null) {
            oldBook.setLongDescription(newBook.getLongDescription());
        }
        if (newBook.getStatus() != null) {
            oldBook.setStatus(newBook.getStatus());
        }
        if (!newBook.getAuthors().isEmpty()) {
            oldBook.setAuthors(newBook.getAuthors());
        }
        if (!newBook.getCategories().isEmpty()) {
            oldBook.setCategories(newBook.getCategories());
        }

        return oldBook;
    }


    /**
     * Retrieves a page of books by a specific author.
     *
     * @param author The name of the author to search for.
     * @param page   The page number (zero-based) to retrieve.
     * @param size   The number of items per page.
     * @return A ResponseEntity containing a Page of Book objects that match the given author,
     *         or an INTERNAL_SERVER_ERROR response if an exception occurs during the process.
     */
    public ResponseEntity<Page<Book>> findBookByAuthor(String author, int page, int size) {
        try {

            List<Book> list = bookRepo.findBookByAuthorName(author);
            Pageable pageable = PageRequest.of(page, size);
            Page<Book> bookPage = new PageImpl<>(list, pageable, list.size());
            return new ResponseEntity<>(bookPage, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("find by author went into error :- ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Endpoint for adding multiple books to the database.
     *
     * @param list A list of Book entities to be added.
     * @return ResponseEntity indicating the status of the operation.
     */
    public ResponseEntity<String> addMultipleBooks(List<Book> list) {
        try {

            if (!list.isEmpty()) {
                for(Book book:list) {
                    book.setDate(new Date());
                    bookRepo.save(book);
                }
                return new ResponseEntity<>(list.size()+" books added",HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("empty list",HttpStatus.NOT_ACCEPTABLE);
            }

        } catch (Exception e) {
            logger.error("multiple add method got into error :- ",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Retrieves a paginated list of books belonging to a specific category.
     *
     * @param categoryName The category name to filter the books.
     * @param page     The page number for pagination.
     * @param size     The number of items per page.
     * @return A ResponseEntity containing a Page of books in the specified category.
     *         Returns HttpStatus. OK if successful, or HttpStatus.INTERNAL_SERVER_ERROR on failure.
     */
    public ResponseEntity<Page<Book>> findBooksByCategoryName(String categoryName, Integer page, Integer size) {
        try {
            Page<Book> bookPage = bookRepo.findBookByCategoryName(categoryName, PageRequest.of(page, size));
            return new ResponseEntity<>(bookPage, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while searching books by category name", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * Searches for books based on the specified search term, including book title, ISBN, descriptions,
     * status, authors, and categories.
     *
     * @param search The search term to filter and search for books.
     * @param page   The page number for pagination.
     * @param size   The number of items per page.
     * @return A ResponseEntity containing a Page of books matching the search term.
     *         Returns HttpStatus. OK if successful, or HttpStatus.INTERNAL_SERVER_ERROR on failure.
     */
    public ResponseEntity<Page<Book>> searchBooks(String search, Integer page, Integer size) {
        try {
            Page<Book> bookPage = bookRepo.searchBooks(search, PageRequest.of(page, size));
            return new ResponseEntity<>(bookPage, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while searching :- ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
