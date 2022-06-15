package ru.otus.dsokolov.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.dsokolov.domain.Author;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.domain.Genre;
import ru.otus.dsokolov.repository.BookRepository;
import ru.otus.dsokolov.rest.dto.BookDto;
import ru.otus.dsokolov.security.CustomUserDetailsService;
import ru.otus.dsokolov.service.BookServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    CustomUserDetailsService customUserDetailsService;
    @MockBean
    private BookServiceImpl bookService;
    @MockBean
    private BookRepository bookRepository;

    private static final String BOOK_TITLE = "Оно";
    private static final long BOOK_AUTHOR_ID = 5;
    private static final String BOOK_AUTHOR_NAME = "Стивен Кинг";
    private static final long BOOK_GENRE_ID = 4;
    private static final String BOOK_GENRE_NAME = "ужасы";

    @WithMockUser(
            username = "Admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void shouldReturnCorrectBookList() throws Exception {
        List<Book> bookList = List.of(
                new Book(1, BOOK_TITLE,
                        new Author(BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME), new Genre(BOOK_GENRE_ID, BOOK_GENRE_NAME)),
                new Book(2, BOOK_TITLE + 2,
                        new Author(BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME), new Genre(BOOK_GENRE_ID, BOOK_GENRE_NAME)));

        given(bookService.findAll()).willReturn(bookList);

        List<BookDto> expectedResult = bookList.stream()
                .map(BookDto::toDto).collect(Collectors.toList());

        mvc.perform(get("/api/books/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @WithMockUser(
            username = "Admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void shouldReturnCorrectBookById() throws Exception {
        Book book = new Book(1, BOOK_TITLE,
                new Author(BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME), new Genre(BOOK_GENRE_ID, BOOK_GENRE_NAME));
        given(bookService.getById(1)).willReturn(book);
        BookDto expectedResult = BookDto.toDto(book);

        mvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @WithMockUser(
            username = "Admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void shouldCorrectSaveNewBook() throws Exception {
        Book book = new Book(1, BOOK_TITLE,
                new Author(BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME), new Genre(BOOK_GENRE_ID, BOOK_GENRE_NAME));
        given(bookService.createBook(any())).willReturn(book);
        String expectedResult = mapper.writeValueAsString(BookDto.toDto(book));

        mvc.perform(post("/api/books").contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }

    @WithMockUser(
            username = "Admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void shouldCorrectUpdateBookTitle() throws Exception {
        Book book = new Book(1, BOOK_TITLE,
                new Author(BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME), new Genre(BOOK_GENRE_ID, BOOK_GENRE_NAME));
        BookDto bookDto = BookDto.toDto(book);

        given(bookService.getById(1)).willReturn(book);
        given(bookRepository.save(any())).willAnswer(invocation -> invocation.getArgument(0));

        Book expectedBook = new Book(1, BOOK_TITLE + 2,
                new Author(BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME), new Genre(BOOK_GENRE_ID, BOOK_GENRE_NAME));
        String expectedResult = mapper.writeValueAsString(BookDto.toDto(expectedBook));

        mvc.perform(patch("/api/books/{id}/title", 1).param("title", expectedBook.getTitle())
                        .content(expectedResult))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }

    @WithMockUser(
            username = "Admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void shouldCorrectDeleteBookById() throws Exception {
        mvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk());
        verify(bookService, times(1)).deleteBook(1);
    }
}
