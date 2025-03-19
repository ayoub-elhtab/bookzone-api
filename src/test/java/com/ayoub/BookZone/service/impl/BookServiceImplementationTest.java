package com.ayoub.BookZone.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ayoub.BookZone.model.Book;
import com.ayoub.BookZone.model.BookEntity;
import com.ayoub.BookZone.repository.BookRepository;
import static com.ayoub.BookZone.TestData.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplementationTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImplementation underTest;

    @Test
    public void shouldSaveBookSuccessfully(){
        // Arrange
        Book book = testBook();
        BookEntity bookEntity = testBookEntity();

        // Mock the calls
        when(bookRepository.save(any(BookEntity.class))).thenReturn(bookEntity);

        // Act
        Book createdBook = underTest.create(book);
        
        // Assert
        assertThat(createdBook).isNotNull();
        assertThat(createdBook.getId()).isEqualTo(book.getId());
        assertThat(createdBook.getTitle()).isEqualTo(book.getTitle());
        assertThat(createdBook.getAuthor()).isEqualTo(book.getAuthor());
        assertThat(createdBook.getCategory()).isEqualTo(book.getCategory());
        
        verify(bookRepository, times(1)).save(any(BookEntity.class));
    }

    @Test
    public void shouldThrowExceptionWhenCreatingNullBook() {
        assertThatThrownBy(() -> underTest.create(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldReturnTrueWhenBookExists(){  
        Book book = testBook();

        when(bookRepository.existsById(any())).thenReturn(true);

        boolean doesBookExist  = underTest.isBookExists(book);
        
        assertThat(doesBookExist).isTrue();
        verify(bookRepository, times(1)).existsById(book.getId());
    }

    @Test
    public void shouldReturnFalseWhenBookDoesNotExist(){
        Book book = testBook();

        when(bookRepository.existsById(any())).thenReturn(false);

        boolean doesBookExist  = underTest.isBookExists(book);
        
        assertThat(doesBookExist).isFalse();
        verify(bookRepository, times(1)).existsById(book.getId());
    }

    @Test
    public void shouldReturnListOfBooksWhenBooksExist(){
        BookEntity bookEntity = testBookEntity();

        when(bookRepository.findAll()).thenReturn(List.of(bookEntity));
        
        List<Book> listBooks = underTest.listBooks();
        
        assertThat(listBooks).hasSize(1);
        Book returnedBook = listBooks.get(0);
        assertThat(returnedBook.getId()).isEqualTo(6L);
        assertThat(returnedBook.getTitle()).isEqualTo("The Prince");
        assertThat(returnedBook.getAuthor()).isEqualTo("Niccolò Machiavelli");
        assertThat(returnedBook.getCategory()).isEqualTo("Political Philosophy");
        
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void shouldReturnEmptyListWhenNoBooksExist(){
        when(bookRepository.findAll()).thenReturn(new ArrayList<BookEntity>());
       
        List<Book> listBooks = underTest.listBooks();
        
        assertThat(listBooks).isEmpty();
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void shouldReturnBookWhenFound(){
        Book book = testBook();
        BookEntity bookEntity = testBookEntity();
        
        when(bookRepository.findById(eq(book.getId()))).thenReturn(Optional.of(bookEntity));

        Optional<Book> foundBook = underTest.findById(book.getId());

        assertThat(foundBook)
                .isPresent()
                .hasValueSatisfying(b -> assertThat(b)
                        .usingRecursiveComparison()
                        .isEqualTo(book));

        verify(bookRepository, times(1)).findById(book.getId());
    }

    @Test
    public void shouldReturnEmptyWhenBookNotFound(){
        Long bookId = 6L;

        when(bookRepository.findById(eq(bookId))).thenReturn(Optional.empty());

        Optional<Book> foundBook = underTest.findById(bookId);

        assertThat(foundBook).isEmpty();
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    public void shouldDeleteBookSuccessfully(){
        Long bookId = 6L; 
        
        underTest.deleteBookById(bookId);
        
        verify(bookRepository, times(1)).deleteById(bookId); 
    }


}
