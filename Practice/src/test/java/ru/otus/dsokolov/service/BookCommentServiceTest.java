package ru.otus.dsokolov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.dsokolov.domain.Author;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.domain.BookComment;
import ru.otus.dsokolov.domain.Genre;
import ru.otus.dsokolov.rest.dto.BookCommentDto;
import ru.otus.dsokolov.rest.dto.BookDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с коментариями к книгам должен")
@DataJpaTest
@Import({BookCommentServiceImpl.class, BookServiceImpl.class, AuthorServiceImpl.class, GenreServiceImpl.class})
public class BookCommentServiceTest {

    private static final String BOOK_TITLE = "Оно";
    private static final String BOOK_AUTHOR_NAME = "Стивен Кинг";
    private static final String BOOK_GENRE_NAME = "ужасы";

    private static final String BOOK_COMMENT = "Хорошо";
    private static final String BOOK_COMMENT2 = "Плохо";

    @Autowired
    private BookCommentServiceImpl bookCommentService;
    @Autowired
    private BookServiceImpl bookService;

    private BookComment getBookComment(Book book, String comment, Date dateIns) {
        BookComment bc = new BookComment();
        bc.setComment(comment);
        bc.setDateInsert(dateIns);
        bc.setBook(book);

        return bc;
    }

    @DisplayName("возвращать ожидаемое кол-во коментариев к книге")
    @Test
    void shouldReturnExpectedBookCoomentCount() {
        Book book = bookService.createBook(new BookDto(BOOK_TITLE,
                new Author(1, BOOK_AUTHOR_NAME),
                new Genre(1, BOOK_GENRE_NAME)));
        bookCommentService.createBookComment(new BookCommentDto(BOOK_COMMENT,
                new Date(), book));

        assertThat(bookCommentService.getBookCommentsCount()).isEqualTo(1);
    }

    @DisplayName("возвращать ожидаемый список коментариев")
    @Test
    void shouldReturnExpectedBookCommentList() {
        Book book = bookService.createBook(new BookDto(BOOK_TITLE,
                new Author(1, BOOK_AUTHOR_NAME),
                new Genre(1, BOOK_GENRE_NAME)));
        Date dateIns = new Date();

        bookCommentService.createBookComment(new BookCommentDto(BOOK_COMMENT, dateIns, book));
        bookCommentService.createBookComment(new BookCommentDto(BOOK_COMMENT2, dateIns, book));

        List<BookComment> actualList = bookCommentService.getAllComments();

        List<BookComment> expectedList = new ArrayList<>();
        expectedList.add(getBookComment(book, BOOK_COMMENT, dateIns));
        expectedList.add(getBookComment(book, BOOK_COMMENT2, dateIns));

        assertThat(actualList)
                .usingRecursiveComparison()
                .comparingOnlyFields("comment", "dateInsert", "book")
                .isEqualTo(expectedList);
    }

    @DisplayName("добавлять коментарий к книге")
    @Test
    void shouldCreateBookComment() {
        Book book = bookService.createBook(new BookDto(BOOK_TITLE,
                new Author(1, BOOK_AUTHOR_NAME),
                new Genre(1, BOOK_GENRE_NAME)));
        Date dateIns = new Date();
        bookCommentService.createBookComment(new BookCommentDto(BOOK_COMMENT, dateIns, book));

        List<BookComment> actualList = bookCommentService.getAllComments();

        List<BookComment> expectedList = new ArrayList<>();
        expectedList.add(getBookComment(book, BOOK_COMMENT, dateIns));

        assertThat(actualList)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .comparingOnlyFields("comment", "dateInsert", "book")
                .isEqualTo(expectedList);
    }

    @DisplayName("удалять коментарий к книге по id")
    @Test
    void shouldDeleteBookCommentById() {
        Book book = bookService.createBook(new BookDto(BOOK_TITLE,
                new Author(1, BOOK_AUTHOR_NAME),
                new Genre(1, BOOK_GENRE_NAME)));
        BookComment bc = bookCommentService.createBookComment(new BookCommentDto(BOOK_COMMENT, new Date(), book));
        bookCommentService.createBookComment(new BookCommentDto(BOOK_COMMENT, new Date(), book));
        bookCommentService.createBookComment(new BookCommentDto(BOOK_COMMENT2, new Date(), book));

        bookCommentService.delBookCommentById(bc.getId());

        assertThat(bookCommentService.getBookCommentsCount()).isEqualTo(2);
    }
}
