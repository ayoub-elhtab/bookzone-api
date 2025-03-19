# BookZone API

A RESTful API for managing a bookstore with CRUD operations, file uploads, and metadata storage.

## Overview

BookZone is a comprehensive book management system that allows users to create, read, update, and delete books. It includes features for uploading and storing book cover images and PDF files while maintaining metadata references in a database.

## 📌 Features

- **Book Management**: Complete CRUD operations for books
- **File Handling**: Upload and retrieve book covers and PDF files
- **API Documentation**: Integrated Swagger UI with OpenAPI specification
- **Robust Testing**: Comprehensive unit tests for all endpoints

## 🚀 Technology Used

- Java with Spring Boot
- RESTful API design
- OpenAPI with Swagger for documentation
- JUnit for unit testing
- SQL database (via Spring Data JPA)
- File storage system for book assets

## API Endpoints

### Books

- `GET /api/books/` - Retrieve all books
- `GET /api/books/{id}` - Retrieve a specific book by ID
- `PUT /api/books/{id}` - Create a new book or Update an existing book
- `DELETE /api/books/{id}` - Delete a book

### Files

- `POST /api/files/upload` - Upload book cover image and PDF
- `GET /api/files/download/{type}/{filename}` - Download a specific file

## Installation and Setup

1. Clone the repository
```bash
git clone https://github.com/ayoub-elhtab/bookzone-api.git
cd bookzone-api
```

2. Build the project
```bash
mvn clean install
```

3. Run the application
```bash
mvn spring-boot:run
```

4. Access the Swagger UI
```
http://localhost:8081/swaggerUI/index.html
```

## Configuration

Configuration for file storage locations, database connections, and other settings can be found in the `application.properties` file.

## Testing

Run the test suite with:

```bash
mvn test
```
