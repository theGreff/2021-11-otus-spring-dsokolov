package ru.otus.dsokolov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.dsokolov.domain.Author;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.domain.Genre;
import ru.otus.dsokolov.rest.dto.BookDto;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с книгами должен")
@DataJpaTest
@Import({BookServiceImpl.class, AuthorServiceImpl.class, GenreServiceImpl.class})
public class BookServiceTest {

    private static final String BOOK_TITLE = "Оно";
    private static final long BOOK_AUTHOR_ID = 5;
    private static final String BOOK_AUTHOR_NAME = "Стивен Кинг";
    private static final long BOOK_GENRE_ID = 4;
    private static final String BOOK_GENRE_NAME = "ужасы";

    @Autowired
    private BookServiceImpl bookService;

    private Book getBook(String bookTitle, long authorId, String authorName, long genreId, String genreName) {
        Author author = new Author();
        author.setId(authorId);
        author.setFullName(authorName);

        Genre genre = new Genre();
        genre.setId(genreId);
        genre.setName(genreName);

        Book book = new Book();
        book.setTitle(bookTitle);
        book.setAuthor(author);
        book.setGenre(genre);

        return book;
    }

    @DisplayName("возвращать ожидаемое кол-во книг из БД")
    @Test
    void shouldReturnExpectedBookCount() {
        bookService.createBook(new BookDto(BOOK_TITLE,
                new Author(1, BOOK_AUTHOR_NAME),
                new Genre(1, BOOK_GENRE_NAME)));

        assertThat(bookService.getCount()).isEqualTo(1);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldCreateBook() {
        bookService.createBook(new BookDto(BOOK_TITLE,
                new Author(1, BOOK_AUTHOR_NAME),
                new Genre(1, BOOK_GENRE_NAME)));
        Book bookActual = bookService.getBookByTitle(BOOK_TITLE);

        Book bookExpected = getBook(BOOK_TITLE, BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME, BOOK_GENRE_ID, BOOK_GENRE_NAME);

        assertThat(bookActual)
                .usingRecursiveComparison()
                .ignoringFields("id", "author.id", "genre.id")
                .isEqualTo(bookExpected);
    }

    @DisplayName("удалять книгу из БД")
    @Test
    void shouldDeleteBook() {
        Book book = bookService.createBook(new BookDto(BOOK_TITLE,
                new Author(1, BOOK_AUTHOR_NAME),
                new Genre(1, BOOK_GENRE_NAME)));
        bookService.deleteBook(book.getId());

        assertThat(bookService.getCount()).isEqualTo(0);
    }

    @DisplayName("менять в книге автора")
    @Test
    void shouldChangeBookAuthor() {
        BookDto bookDto = new BookDto(BOOK_TITLE,
                new Author(1, BOOK_AUTHOR_NAME),
                new Genre(1, BOOK_GENRE_NAME));
        Book book = bookService.createBook(bookDto);
        bookDto.setId(book.getId());
        bookDto.setAuthor(new Author(2, "Джоан Роулинг"));
        bookService.update(bookDto);

        Book bookActual = bookService.getBookByTitle(BOOK_TITLE);

        Book bookExpected = getBook(BOOK_TITLE, 1, "Джоан Роулинг", BOOK_GENRE_ID, BOOK_GENRE_NAME);

        assertThat(bookActual)
                .usingRecursiveComparison()
                .ignoringFields("id", "author.id", "genre.id")
                .isEqualTo(bookExpected);
    }

    @DisplayName("менять в книге жанр")
    @Test
    void shouldChangeBookGenre() {
        BookDto bookDto = new BookDto(BOOK_TITLE,
                new Author(1, BOOK_AUTHOR_NAME),
                new Genre(1, BOOK_GENRE_NAME));
        Book book = bookService.createBook(bookDto);
        bookDto.setId(book.getId());
        bookDto.setGenre(new Genre(2, "детектив"));
        bookService.update(bookDto);

        Book bookActual = bookService.getBookByTitle(BOOK_TITLE);

        Book bookExpected = getBook(BOOK_TITLE, BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME, 2, "детектив");

        assertThat(bookActual)
                .usingRecursiveComparison()
                .ignoringFields("id", "author.id", "genre.id")
                .isEqualTo(bookExpected);
    }

    @DisplayName("менять в книге название")
    @Test
    void shouldChangeBookTitle() {
        BookDto bookDto = new BookDto(BOOK_TITLE,
                new Author(1, BOOK_AUTHOR_NAME),
                new Genre(1, BOOK_GENRE_NAME));
        Book book = bookService.createBook(bookDto);
        bookDto.setId(book.getId());
        bookDto.setTitle(BOOK_TITLE + 2);
        bookService.update(bookDto);

        Book bookActual = bookService.getBookByTitle(BOOK_TITLE + 2);

        Book bookExpected = getBook(BOOK_TITLE + 2, BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME, BOOK_GENRE_ID, BOOK_GENRE_NAME);

        assertThat(bookActual)
                .usingRecursiveComparison()
                .ignoringFields("id", "author.id", "genre.id")
                .isEqualTo(bookExpected);
    }

    @DisplayName("возвращать ожидаемую книгу по title")
    @Test
    void shouldReturnExpectedBookByTitle() {
        BookDto bookDto = new BookDto(BOOK_TITLE,
                new Author(1, BOOK_AUTHOR_NAME),
                new Genre(1, BOOK_GENRE_NAME));
        bookService.createBook(bookDto);

        Book bookActual = bookService.getBookByTitle(BOOK_TITLE);

        Book bookExpected = getBook(BOOK_TITLE, BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME, BOOK_GENRE_ID, BOOK_GENRE_NAME);

        assertThat(bookActual)
                .usingRecursiveComparison()
                .ignoringFields("id", "author.id", "genre.id")
                .isEqualTo(bookExpected);
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedBookList() {
        bookService.createBook(new BookDto(BOOK_TITLE,
                new Author(1, BOOK_AUTHOR_NAME),
                new Genre(1, BOOK_GENRE_NAME)));
        bookService.createBook(new BookDto(BOOK_TITLE + 2,
                new Author(1, BOOK_AUTHOR_NAME),
                new Genre(1, BOOK_GENRE_NAME)));

        List<Book> bookActualList = bookService.findAll();

        List<Book> bookExpectedList = new ArrayList<>();
        bookExpectedList.add(getBook(BOOK_TITLE, BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME, BOOK_GENRE_ID, BOOK_GENRE_NAME));
        bookExpectedList.add(getBook(BOOK_TITLE + 2, BOOK_AUTHOR_ID, BOOK_AUTHOR_NAME, BOOK_GENRE_ID, BOOK_GENRE_NAME));

        assertThat(bookActualList)
                .usingRecursiveComparison()
                .ignoringFields("id", "author.id", "genre.id")
                .isEqualTo(bookExpectedList);
    }
}
