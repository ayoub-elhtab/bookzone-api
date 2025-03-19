package com.ayoub.BookZone.service;

import java.util.List;
import java.util.Optional;

import com.ayoub.BookZone.model.Book;

public interface BookService {

	Boolean isBookExists(Book book);
	
	Book create(Book book);
	
	List<Book> listBooks();
	
	Optional<Book> findById(Long id);
	
	void deleteBookById(Long id);
}
