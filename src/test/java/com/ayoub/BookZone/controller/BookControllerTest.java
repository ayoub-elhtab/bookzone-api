package com.ayoub.BookZone.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import com.ayoub.BookZone.model.Book;
import com.ayoub.BookZone.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.ayoub.BookZone.TestData.*;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void testBookIsCreatedReturnsHTTP201() throws JsonProcessingException, Exception {
        Book book = testBook();
        objectMapper = new ObjectMapper();

        when(bookService.isBookExists(any(Book.class))).thenReturn(false);
        when(bookService.create(any(Book.class))).thenReturn(book);
        mockMvc
            .perform(
                put("/api/books/" + book.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(book.getId()))
            .andExpect(jsonPath("$.title").value(book.getTitle()))
            .andExpect(jsonPath("$.author").value(book.getAuthor()))
            .andExpect(jsonPath("$.category").value(book.getCategory()))
            .andDo(MockMvcResultHandlers.print());

            verify(bookService, times(1)).isBookExists(any(Book.class));
            verify(bookService, times(1)).create(any(Book.class));
    }

    @Test
    public void testBookIsUpdatedReturnsHTTP200() throws JsonProcessingException, Exception {
        Book book = testBook();
        objectMapper = new ObjectMapper();

        when(bookService.isBookExists(any(Book.class))).thenReturn(true);
        when(bookService.create(any(Book.class))).thenReturn(book);
        mockMvc
            .perform(
                put("/api/books/" + book.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(book.getId()))
            .andExpect(jsonPath("$.title").value(book.getTitle()))
            .andExpect(jsonPath("$.author").value(book.getAuthor()))
            .andExpect(jsonPath("$.category").value(book.getCategory()))
            .andDo(MockMvcResultHandlers.print());

            verify(bookService, times(1)).isBookExists(any(Book.class));
            verify(bookService, times(1)).create(any(Book.class));
    }

    @Test
    public void testListBooksReturnsHTTP200AndBooksWhenBooksExist() throws Exception {
        Book book = testBook();

        when(bookService.listBooks()).thenReturn(List.of(book));
        
        mockMvc
            .perform(
                get("/api/books/")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$.[0].id").value(book.getId()))
            .andExpect(jsonPath("$.[0].title").value(book.getTitle()))
            .andExpect(jsonPath("$.[0].author").value(book.getAuthor()))
            .andExpect(jsonPath("$.[0].category").value(book.getCategory()))
            .andDo(MockMvcResultHandlers.print());

        verify(bookService, times(1)).listBooks();
    }

    @Test
    public void testListBooksReturnsHTTP200AndEmptyListWhenNoBooksExist() throws Exception {
        
        when(bookService.listBooks()).thenReturn(new ArrayList<Book>());
        mockMvc
            .perform(
                get("/api/books/")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(0))
            .andDo(MockMvcResultHandlers.print());

        verify(bookService, times(1)).listBooks();
    }

    @Test
	public void testRetrieveBookReturnsHTTPp200AndBookWhenExists() throws Exception{
        Book book = testBook();

        when(bookService.findById(any())).thenReturn(Optional.of(book));

        mockMvc
            .perform(
                get("/api/books/" + book.getId())
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(book.getId()))
            .andExpect(jsonPath("$.title").value(book.getTitle()))
            .andExpect(jsonPath("$.author").value(book.getAuthor()))
            .andExpect(jsonPath("$.category").value(book.getCategory()))
            .andDo(MockMvcResultHandlers.print());

            verify(bookService, times(1)).findById(book.getId());

    }

    @Test
	public void testRetrieveBookReturnsHTTP404WhenBookDoesNotExists() throws Exception{
        Long bookId = 6L;

        when(bookService.findById(bookId)).thenReturn(Optional.empty());

        mockMvc.perform(
                get("/api/books/" + bookId)
            )
            .andExpect(status().isNotFound())
            .andDo(MockMvcResultHandlers.print());
        
        verify(bookService, times(1)).findById(bookId);
    }

    @Test
    public void testDeleteBookReturnsHTTP204NoContentWhenBookDeleted() throws Exception {
        Long bookId = 6L;
        
        doNothing().when(bookService).deleteBookById(bookId);
        
        mockMvc.perform(
            delete("/api/books/"+ bookId)
            )
            .andExpect(status().isNoContent())
            .andDo(MockMvcResultHandlers.print());
        
        verify(bookService, times(1)).deleteBookById(bookId);

    }

   
}
