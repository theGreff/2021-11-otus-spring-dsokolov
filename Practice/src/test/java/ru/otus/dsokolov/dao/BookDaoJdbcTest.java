package ru.otus.dsokolov.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.dsokolov.domain.Author;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.domain.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import({BookDaoJdbs.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
public class BookDaoJdbcTest {

    private static final String BOOK_TITLE = "Оно";
    private static final long BOOK_AUTHOR_ID = 5;
    private static final String BOOK_AUTHOR_NAME = "Стивен Кинг";
    private static final long BOOK_GENRE_ID = 4;
    private static final String BOOK_GENRE_NAME = "ужасы";

    @Autowired
    BookDaoJdbs bookDaoJdbs;

    @DisplayName("возвращать ожидаемое кол-во книг из БД")
    @Test
    void shouldReturnExpectedBookCount() {
        Book bookExpected = new Book(BOOK_TITLE, new Author(BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME),
                new Genre(BOOK_GENRE_ID, BOOK_GENRE_NAME));
        bookDaoJdbs.insert(bookExpected);

        assertThat(bookDaoJdbs.getCount()).isEqualTo(1);
    }

    @DisplayName("возвращать ожидаемую книгу по title")
    @Test
    void shouldReturnExpectedBookByTitle() {
        Book bookExpected = new Book(BOOK_TITLE, new Author(BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME),
                new Genre(BOOK_GENRE_ID, BOOK_GENRE_NAME));
        bookDaoJdbs.insert(bookExpected);
        Book bookActual = bookDaoJdbs.getByTitle(BOOK_TITLE);

        assertThat(bookActual)
                .usingRecursiveComparison().comparingOnlyFields("title", "author", "genre")
                .isEqualTo(bookExpected);
    }

    @DisplayName("возвращать ожидаемую книгу по id")
    @Test
    void shouldReturnExpectedBookById() {
        Book bookExpected = new Book(BOOK_TITLE, new Author(BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME),
                new Genre(BOOK_GENRE_ID, BOOK_GENRE_NAME));
        bookDaoJdbs.insert(bookExpected);
        Book bookActual = bookDaoJdbs.getById(bookDaoJdbs.getByTitle(BOOK_TITLE).getId());

        assertThat(bookActual)
                .usingRecursiveComparison().comparingOnlyFields("title", "author", "genre")
                .isEqualTo(bookExpected);
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedBookList() {
        List<Book> bookExpectedList = new ArrayList<>();

        Book bookExpected = new Book(BOOK_TITLE, new Author(BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME),
                new Genre(BOOK_GENRE_ID, BOOK_GENRE_NAME));
        bookDaoJdbs.insert(bookExpected);
        bookExpectedList.add(bookExpected);

        Book bookExpected2 = new Book(BOOK_TITLE + 2, new Author(BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME),
                new Genre(BOOK_GENRE_ID, BOOK_GENRE_NAME));
        bookDaoJdbs.insert(bookExpected2);
        bookExpectedList.add(bookExpected2);

        List<Book> bookActualList = bookDaoJdbs.getAll();

        assertThat(bookActualList)
                .usingRecursiveComparison().comparingOnlyFields("title", "author", "genre")
                .isEqualTo(bookExpectedList);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Book bookExpected = new Book(BOOK_TITLE, new Author(BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME),
                new Genre(BOOK_GENRE_ID, BOOK_GENRE_NAME));
        bookDaoJdbs.insert(bookExpected);
        Book bookActual = bookDaoJdbs.getByTitle(BOOK_TITLE);

        assertThat(bookActual)
                .usingRecursiveComparison().comparingOnlyFields("title", "author", "genre")
                .isEqualTo(bookExpected);
    }

    @DisplayName("менять в книге наименование")
    @Test
    void shouldUpdateBook() {
        Book book = new Book(BOOK_TITLE, new Author(BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME),
                new Genre(BOOK_GENRE_ID, BOOK_GENRE_NAME));
        bookDaoJdbs.insert(book);

        Book bookExpected = bookDaoJdbs.getByTitle(BOOK_TITLE);
        bookExpected.setTitle(BOOK_TITLE + 2);
        bookDaoJdbs.update(bookExpected);

        Book bookActual = bookDaoJdbs.getByTitle(BOOK_TITLE + 2);

        assertThat(bookActual)
                .usingRecursiveComparison().comparingOnlyFields("title", "author", "genre")
                .isEqualTo(bookExpected);
    }

    @DisplayName("удалять книгу из БД по id")
    @Test
    void shouldDeleteBookById() {
        Book book = new Book(BOOK_TITLE, new Author(BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME),
                new Genre(BOOK_GENRE_ID, BOOK_GENRE_NAME));
        bookDaoJdbs.insert(book);

        book = bookDaoJdbs.getByTitle(BOOK_TITLE);
        bookDaoJdbs.deleteById(book.getId());
        bookDaoJdbs.getCount();

        assertThat(bookDaoJdbs.getCount()).isEqualTo(0);
    }
}
