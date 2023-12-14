I've combined the two READMEs into one, providing both the overview and detailed endpoint information:

# Book Service API

## Overview
This project is a Spring Boot-based RESTful API for managing a collection of books. It supports basic CRUD operations for books, including searching by various criteria.

## Technologies Used
- [Spring Boot](https://spring.io/projects/spring-boot): Framework for creating stand-alone, production-grade Spring-based Applications.
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa): Simplifies data access using the Java Persistence API (JPA).
- [PostgreSQL](https://www.postgresql.org/): Database system for storing book information.
- [Apache PDFBox](https://pdfbox.apache.org/): Library for PDF document manipulation.
- [Lombok](https://projectlombok.org/): Library to simplify Java code by reducing boilerplate code.
- [JUnit](https://junit.org/junit5/): Testing framework for unit testing.

## Prerequisites
- Java 11 or higher
- PostgreSQL database
- Maven

## Getting Started
1. Clone the repository: `git clone <repository-url>`
2. Navigate to the project directory: `cd multi-part`
3. Configure the database connection in `application.properties` or `application.yml`.
4. Build the project: `mvn clean install`
5. Run the application: `mvn spring-boot:run`

## API Endpoints

### Get All Books
```http
GET /book/
```
Retrieve a paginated list of all books.

#### Parameters
- `page` (optional): Page number (default: 0)
- `size` (optional): Number of items per page (default: 10)

### Get Book by Author
```http
GET /book/author/{author}
```
Retrieve books by a specific author.

#### Parameters
- `author`: Name of the author

### Get Book by Category
```http
GET /book/category/{category}
```
Retrieve books by a specific category.

#### Parameters
- `category`: Name of the category

### Search Books
```http
GET /book/search/{search}
```
Search books based on a search term.

#### Parameters
- `search`: Search term

### Add a Book
```http
PUT /book/add
```
Add a new book.

#### Request Body
```json
{
    "title": "Book Title",
    "isbn": "ISBN-1234567890",
    "pageCount": 300,
    "thumbnailUrl": "https://example.com/thumbnail.jpg",
    "shortDescription": "Short description of the book.",
    "longDescription": "Long description of the book.",
    "status": "PUBLISH",
    "authors": ["Author1", "Author2"],
    "categories": ["Category1", "Category2"],
    "date": "2023-12-14T09:40:20.291+00:00"
}
```

### Update a Book
```http
PATCH /book/update/{id}
```
Update an existing book.

#### Parameters
- `id`: ID of the book to update

#### Request Body
```json
{
    "title": "Updated Title",
    "shortDescription": "Updated short description."
}
```

### Delete a Book
```http
DELETE /book/delete/{id}
```
Delete an existing book.

#### Parameters
- `id`: ID of the book to delete

### Add Multiple Books
```http
PUT /book/add/all
```
Add multiple books.

#### Request Body
```json
[
    {
        "title": "Book Title 1"
    },
    {
        "title": "Book Title 2"
    }
]
```

### Find Books by Publisher
```http
GET /book/publisher/{publisher}
```
Retrieve books by a specific publisher.

#### Parameters
- `publisher`: Name of the publisher

### Search Books by Language
```http
GET /book/language/{language}
```
Search books by a specific language.

#### Parameters
- `language`: Language of the book

## Contributing
Feel free to open issues or submit pull requests.

## License
This project is licensed under the [MIT License](LICENSE).
