package com.multi.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The {@code Book} class represents a book entity in the application.
 * It is annotated with {@code @Entity} to indicate that it is a JPA entity.
 * The {@code @Data} annotation is used to generate boilerplate code for
 * getters, setters, equals, hashCode, and toString methods.
 *
 * The class defines various attributes such as ID, title, ISBN, page count,
 * thumbnail URL, short and long descriptions, status, authors, categories,
 * and publication date.
 *
 * The {@code @Id} annotation specifies the primary key, and {@code @GeneratedValue}
 * with {@code GenerationType.IDENTITY} indicates that the ID should be generated
 * automatically by the underlying database.
 *
 * Additional annotations like {@code @Column} are used to specify column lengths
 * for certain attributes. For example, the title can be up to 400 characters long.
 *
 * The {@code @ElementCollection} annotation is used for collections of simple
 * types (like {@code List<String>}) and is associated with a separate table
 * (e.g., {@code book_authors} and {@code book_categories}) to store the elements.
 *
 * The {@code @CollectionTable} and {@code @Column} annotations define the
 * table and column details for the elements in the collection.
 *
 * This class is intended to be used in conjunction with a JPA-enabled
 * persistence layer to interact with a relational database.
 *
 *
 * @author AbKumar
 */
@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 400)
    private String title;

    @Column(length = 100)
    private String isbn;
    private Long pageCount;

    @Column(length = 400)
    private String thumbnailUrl;

    @Column(length = 2000)
    private String shortDescription;

    @Column(length = 5000)
    private String longDescription;
    private String status;


    /**
     * The {@code authors} attribute represents a collection of authors associated with the book.
     * It is annotated with {@code @ElementCollection} to indicate that it is a collection
     * of simple types, and the {@code @CollectionTable} annotation specifies the name
     * of the table that will store the authors, which is "book_authors". The association
     * is established through a join table, and the foreign key column in that table is named
     * "author_id". Each author is stored as a separate record in the associated table.
     *
     * The {@code @Column} annotation with name "author" is used to define the column name
     * for the elements in the authors collection. In this case, it represents the name
     * of an author.
     */
    @ElementCollection
    @CollectionTable(name = "book_authors", joinColumns = @JoinColumn(name = "author_id"))
    @Column(name = "author")
    private List<String> authors;


    /**
     * The {@code categories} attribute represents a collection of categories associated with the book.
     * Similar to the authors attribute, it is annotated with {@code @ElementCollection} to indicate
     * a collection of simple types. The {@code @CollectionTable} annotation specifies the name
     * of the table that will store the categories, which is "book_categories". The association
     * is established through a join table, and the foreign key column in that table is named
     * "category_id". Each category is stored as a separate record in the associated table.
     *
     * The {@code @Column} annotation with name "categories" is used to define the column name
     * for the elements in the categories collection. In this case, it represents the name
     * of a book category.
     */
    @ElementCollection
    @CollectionTable(name = "book_categories", joinColumns = @JoinColumn(name = "category_id"))
    @Column(name = "categories")
    private List<String> categories;
    private Date date;

}
