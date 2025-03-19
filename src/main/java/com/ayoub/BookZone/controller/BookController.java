package com.ayoub.BookZone.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayoub.BookZone.model.Book;
import com.ayoub.BookZone.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

	private final BookService bookService;

	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Book> createUpdateBook(@PathVariable final Long id, @RequestBody Book book){
		book.setId(id);
		final Boolean isBookExists = bookService.isBookExists(book);
		final Book createdBook = bookService.create(book);
		
		if(isBookExists) {
			return new ResponseEntity<Book>(createdBook, HttpStatus.OK);
		}else {
			return new ResponseEntity<Book>(createdBook, HttpStatus.CREATED);
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Book>> getAllBooks(){
		final List<Book> books = bookService.listBooks();
		return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBook(@PathVariable final Long id){
		final Optional<Book> foundBook = bookService.findById(id);
		return foundBook
				.map(book -> new ResponseEntity<Book>(book, HttpStatus.OK))
				.orElse(new ResponseEntity<Book>(HttpStatus.NOT_FOUND));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable final Long id){
		
		bookService.deleteBookById(id);
		return new ResponseEntity<> (HttpStatus.NO_CONTENT);
		
	}
	
	
}
