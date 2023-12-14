# Book Service API

This API provides endpoints for managing and retrieving information about books.

## Base URL
All endpoints can be accessed using the following base URL:

```
http://localhost:8080/book
```

## Endpoints

### 1. Add a Book

- **URL:** `/add`
- **Method:** PUT
- **Request Body:** Book details in JSON format.
- **Response:** Returns a message indicating the success or failure of the operation.

### 2. Get All Books

- **URL:** `/`
- **Method:** GET
- **Parameters:**
    - `page` (optional, default: 0): Page number for pagination.
    - `size` (optional, default: 10): Number of items per page.
- **Response:** Returns a page of books.

### 3. Delete a Book by ID

- **URL:** `/delete/{id}`
- **Method:** DELETE
- **Path Variable:** `id` - ID of the book to be deleted.
- **Response:** Returns a message indicating the success or failure of the operation.

### 4. Update a Book by ID

- **URL:** `/update/{id}`
- **Method:** PATCH
- **Path Variable:** `id` - ID of the book to be updated.
- **Request Body:** Updated book details in JSON format.
- **Response:** Returns a message indicating the success or failure of the operation.

### 5. Find Books by Author

- **URL:** `/author/{author}`
- **Method:** GET
- **Path Variable:** `author` - Author's name.
- **Parameters:**
    - `page` (optional, default: 0): Page number for pagination.
    - `size` (optional, default: 10): Number of items per page.
- **Response:** Returns a page of books written by the specified author.

### 6. Find Books by Category

- **URL:** `/category/{category}`
- **Method:** GET
- **Path Variable:** `category` - Book category.
- **Parameters:**
    - `page` (optional, default: 0): Page number for pagination.
    - `size` (optional, default: 10): Number of items per page.
- **Response:** Returns a page of books belonging to the specified category.

### 7. Add Multiple Books

- **URL:** `/add/all`
- **Method:** PUT
- **Request Body:** List of books in JSON format.
- **Response:** Returns a message indicating the success or failure of the operation.

### 8. Search Books

- **URL:** `/search/{search}`
- **Method:** GET
- **Path Variable:** `search` - Search term.
- **Parameters:**
    - `page` (optional, default: 0): Page number for pagination.
    - `size` (optional, default: 10): Number of items per page.
- **Response:** Returns a page of books matching the specified search term.

## Notes

- All endpoints support pagination for retrieving a subset of the results.
- The base URL for the API is `http://localhost:8080/book`.
- Make sure to replace `{id}`, `{author}`, `{category}`, and `{search}` with actual values when making requests.

Feel free to explore and interact with the various endpoints to manage and retrieve information about books.