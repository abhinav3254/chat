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
     * Custom query to find books by the specified author name using a native SQL query.
     *
     * @param name The name of the author to search for.
     * @return A list of Book entities that match the given author name.
     */
    @Query(nativeQuery = true, value = "SELECT * FROM book b\n" +
            "JOIN book_authors ba ON b.id = ba.author_id\n" +
            "WHERE ba.author LIKE %:name%")
    List<Book> findBookByAuthorName(@Param("name") String name);

}
