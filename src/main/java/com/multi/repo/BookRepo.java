package com.multi.repo;

import com.multi.entity.Book;
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


    // JPQL (Java Persistence Query Language)
    @Query("SELECT b FROM Book b JOIN b.categories bc WHERE bc LIKE %:name%")
    List<Book> findBookByCategoryName(@Param("name") String name);


}
