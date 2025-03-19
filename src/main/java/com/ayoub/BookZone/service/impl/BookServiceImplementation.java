package com.ayoub.BookZone.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ayoub.BookZone.model.Book;
import com.ayoub.BookZone.model.BookEntity;
import com.ayoub.BookZone.repository.BookRepository;
import com.ayoub.BookZone.service.BookService;

@Service
public class BookServiceImplementation implements BookService{
	private static final Logger logger = LogManager.getLogger(BookServiceImplementation.class);

	private final BookRepository bookRepository;
	
	@Autowired
	public BookServiceImplementation(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	private BookEntity bookToBookEntity(Book book) {
		return BookEntity.builder()
				.id(book.getId())
				.title(book.getTitle())
				.author(book.getAuthor())
				.category(book.getCategory())
				.language(book.getLanguage())
				.description(book.getDescription())
				.pages(book.getPages())
				.imageUrl(book.getImageUrl())
				.pdfUrl(book.getPdfUrl())
				.build();
	}
	private Book bookEntityToBook(BookEntity bookEntity) {
		return Book.builder()
				.id(bookEntity.getId())
				.title(bookEntity.getTitle())
				.author(bookEntity.getAuthor())
				.category(bookEntity.getCategory())
				.language(bookEntity.getLanguage())
				.description(bookEntity.getDescription())
				.pages(bookEntity.getPages())
				.imageUrl(bookEntity.getImageUrl())
				.pdfUrl(bookEntity.getPdfUrl())
				.build();
	}
	@Override
	public Book create(final Book book) {
		final BookEntity bookEntity = bookToBookEntity(book);
		final BookEntity savedBookEntity = bookRepository.save(bookEntity);
		return bookEntityToBook(savedBookEntity);
	}

	@Override
	public Boolean isBookExists(Book book) {
		return bookRepository.existsById(book.getId());
	}

	@Override
	public List<Book> listBooks() {
		final List<BookEntity> listOfBooks = bookRepository.findAll();
		return listOfBooks.stream().map(book -> bookEntityToBook(book)).collect(Collectors.toList());		
	}

	@Override
	public Optional<Book> findById(Long id) {
		final Optional<BookEntity> foundBook = bookRepository.findById(id);
		return foundBook.map(book -> bookEntityToBook(book));
	}

	@Override
	public void deleteBookById(Long id) {
		try {
			bookRepository.deleteById(id);
		}
		catch(final EmptyResultDataAccessException e) {
			logger.debug("Attempting to delete a non-existing book with id "+ id , e);
		}
		
	}
	

}
