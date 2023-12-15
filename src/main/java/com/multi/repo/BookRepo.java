package com.multi.repo;

import com.multi.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository interface for managing Book entities.
 */
@Repository
public interface BookRepo extends JpaRepository<Book,Long> {


    /**
     * JPQL (Java Persistence Query Language) find books by the specified author name using a native SQL query.
     *
     * @param name The name of the author to search for.
     * @return A list of Book entities that match the given author name.
     */
    @Query("SELECT b FROM Book b JOIN b.authors ba WHERE ba LIKE %:name%")
    List<Book> findBookByAuthorName(@Param("name") String name);


    /**
     * Custom query to find books by category name.
     *
     * @param name The category name to search for in the book's categories.
     * @return List of books belonging to the specified category.
     */

    // JPQL (Java Persistence Query Language)
    @Query("SELECT b FROM Book b JOIN b.categories bc WHERE bc LIKE %:name%")
    List<Book> findBookByCategoryName(@Param("name") String name);



    /**
     * Custom query to search for books based on various criteria.
     *
     * @param searchTerm The term to search for in the book's title, ISBN, short and long descriptions,
     *                   status, authors, and categories.
     * @return List of books matching the search criteria.
     */
    @Query("SELECT b FROM Book b " +
            "WHERE lower(b.title) LIKE lower(concat('%', :searchTerm, '%')) " +
            "   OR lower(b.isbn) LIKE lower(concat('%', :searchTerm, '%')) " +
            "   OR lower(b.shortDescription) LIKE lower(concat('%', :searchTerm, '%')) " +
            "   OR lower(b.longDescription) LIKE lower(concat('%', :searchTerm, '%')) " +
            "   OR lower(b.status) LIKE lower(concat('%', :searchTerm, '%')) " +
            "   OR :searchTerm IN elements(b.authors) " +
            "   OR :searchTerm IN elements(b.categories)")
    Page<Book> searchBooks(@Param("searchTerm") String searchTerm, Pageable pageable);


}
