package com.multi.repo;

import com.multi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepo extends JpaRepository<Book,Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM book b\n" +
            "JOIN book_authors ba ON b.id = ba.author_id\n" +
            "WHERE ba.author LIKE %:name%")
    List<Book> findBookByAuthorName(@Param("name") String name);

}
