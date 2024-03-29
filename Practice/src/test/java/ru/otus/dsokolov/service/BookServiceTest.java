package ru.otus.dsokolov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.dsokolov.dao.AuthorDaoJdbc;
import ru.otus.dsokolov.dao.BookDaoJdbs;
import ru.otus.dsokolov.dao.GenreDaoJdbc;
import ru.otus.dsokolov.domain.Author;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.domain.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с книгами должен")
@JdbcTest
@Import({BookServiceImpl.class, BookDaoJdbs.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
public class BookServiceTest {

    private static final String BOOK_TITLE = "Оно";
    private static final long BOOK_AUTHOR_ID = 5;
    private static final String BOOK_AUTHOR_NAME = "Стивен Кинг";
    private static final long BOOK_GENRE_ID = 4;
    private static final String BOOK_GENRE_NAME = "ужасы";

    @Autowired
    BookServiceImpl bookService;

    @DisplayName("возвращать ожидаемое кол-во книг из БД")
    @Test
    void shouldReturnExpectedBookCount() {
        bookService.createBook(BOOK_TITLE, BOOK_AUTHOR_NAME, BOOK_GENRE_NAME);

        assertThat(bookService.getBooksCount()).isEqualTo(1);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldCreateBook() {
        bookService.createBook(BOOK_TITLE, BOOK_AUTHOR_NAME, BOOK_GENRE_NAME);
        Book bookActual = bookService.getBookByTitle(BOOK_TITLE);

        Book bookExpected = new Book(BOOK_TITLE, new Author(BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME),
                new Genre(BOOK_GENRE_ID, BOOK_GENRE_NAME));

        assertThat(bookActual)
                .usingRecursiveComparison().comparingOnlyFields("title", "author", "genre")
                .isEqualTo(bookExpected);
    }

    @DisplayName("удалять книгу из БД")
    @Test
    void shouldDeleteBook() {
        bookService.createBook(BOOK_TITLE, BOOK_AUTHOR_NAME, BOOK_GENRE_NAME);
        bookService.deleteBook(BOOK_TITLE);

        assertThat(bookService.getBooksCount()).isEqualTo(0);
    }

    @DisplayName("менять в книге автора")
    @Test
    void shouldChangeBookAuthor() {
        bookService.createBook(BOOK_TITLE, BOOK_AUTHOR_NAME, BOOK_GENRE_NAME);
        bookService.changeAuthor(BOOK_TITLE, "Джоан Роулинг");
        Book bookActual = bookService.getBookByTitle(BOOK_TITLE);

        Book bookExpected = new Book(BOOK_TITLE, new Author(1, "Джоан Роулинг"),
                new Genre(BOOK_GENRE_ID, BOOK_GENRE_NAME));

        assertThat(bookActual)
                .usingRecursiveComparison().comparingOnlyFields("title", "author", "genre")
                .isEqualTo(bookExpected);
    }

    @DisplayName("менять в книге жанр")
    @Test
    void shouldChangeBookGenre() {
        bookService.createBook(BOOK_TITLE, BOOK_AUTHOR_NAME, BOOK_GENRE_NAME);
        bookService.changeGenre(BOOK_TITLE, "детектив");
        Book bookActual = bookService.getBookByTitle(BOOK_TITLE);

        Book bookExpected = new Book(BOOK_TITLE, new Author(BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME),
                new Genre(2, "детектив"));

        assertThat(bookActual)
                .usingRecursiveComparison().comparingOnlyFields("title", "author", "genre")
                .isEqualTo(bookExpected);
    }

    @DisplayName("менять в книге название")
    @Test
    void shouldChangeBookTitle() {
        bookService.createBook(BOOK_TITLE, BOOK_AUTHOR_NAME, BOOK_GENRE_NAME);
        bookService.changeTitle(BOOK_TITLE, BOOK_TITLE + 2);
        Book bookActual = bookService.getBookByTitle(BOOK_TITLE + 2);

        Book bookExpected = new Book(BOOK_TITLE + 2, new Author(BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME),
                new Genre(BOOK_GENRE_ID, BOOK_GENRE_NAME));

        assertThat(bookActual)
                .usingRecursiveComparison().comparingOnlyFields("title", "author", "genre")
                .isEqualTo(bookExpected);
    }

    @DisplayName("возвращать ожидаемую книгу по title")
    @Test
    void shouldReturnExpectedBookByTitle() {
        bookService.createBook(BOOK_TITLE, BOOK_AUTHOR_NAME, BOOK_GENRE_NAME);
        Book bookActual = bookService.getBookByTitle(BOOK_TITLE);

        Book bookExpected = new Book(BOOK_TITLE, new Author(BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME),
                new Genre(BOOK_GENRE_ID, BOOK_GENRE_NAME));

        assertThat(bookActual)
                .usingRecursiveComparison().comparingOnlyFields("title", "author", "genre")
                .isEqualTo(bookExpected);
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedBookList() {
        bookService.createBook(BOOK_TITLE, BOOK_AUTHOR_NAME, BOOK_GENRE_NAME);
        bookService.createBook(BOOK_TITLE + 2, BOOK_AUTHOR_NAME, BOOK_GENRE_NAME);
        List<Book> bookActualList = bookService.getAllBooks();

        List<Book> bookExpectedList = new ArrayList<>();
        bookExpectedList.add(new Book(BOOK_TITLE, new Author(BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME),
                new Genre(BOOK_GENRE_ID, BOOK_GENRE_NAME)));
        bookExpectedList.add(new Book(BOOK_TITLE + 2, new Author(BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME),
                new Genre(BOOK_GENRE_ID, BOOK_GENRE_NAME)));

        assertThat(bookActualList)
                .usingRecursiveComparison().comparingOnlyFields("title", "author", "genre")
                .isEqualTo(bookExpectedList);
    }
}
