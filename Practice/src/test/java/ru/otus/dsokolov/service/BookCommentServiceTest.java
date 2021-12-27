package ru.otus.dsokolov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.dsokolov.base.Utils;
import ru.otus.dsokolov.dao.AuthorDaoJpa;
import ru.otus.dsokolov.dao.BookCommentDaoJpa;
import ru.otus.dsokolov.dao.BookDaoJpa;
import ru.otus.dsokolov.dao.GenreDaoJpa;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.domain.BookComment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с коментариями к книгам должен")
@DataJpaTest
@Import({BookCommentServiceImpl.class, BookServiceImpl.class, AuthorServiceImpl.class, GenreServiceImpl.class,
        BookDaoJpa.class, AuthorDaoJpa.class, GenreDaoJpa.class, BookCommentDaoJpa.class})
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
    @Autowired
    private TestEntityManager em;

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
        bookService.createBook(BOOK_TITLE, BOOK_AUTHOR_NAME, BOOK_GENRE_NAME);
        bookCommentService.createBookComment(BOOK_TITLE, BOOK_COMMENT, new Date());

        assertThat(bookCommentService.getBookCommentsCount()).isEqualTo(1);
    }

    @DisplayName("возвращать ожидаемый список коментариев")
    @Test
    void shouldReturnExpectedBookCommentList() {
        Book book = bookService.createBook(BOOK_TITLE, BOOK_AUTHOR_NAME, BOOK_GENRE_NAME);
        Date dateIns = new Date();
        bookCommentService.createBookComment(BOOK_TITLE, BOOK_COMMENT, dateIns);
        bookCommentService.createBookComment(BOOK_TITLE, BOOK_COMMENT2, dateIns);
        List<BookComment> actualList = bookCommentService.getAllComments();

        List<BookComment> expectedList = new ArrayList<>();
        expectedList.add(getBookComment(book, BOOK_COMMENT, dateIns));
        expectedList.add(getBookComment(book, BOOK_COMMENT2, dateIns));

        assertThat(actualList)
                .usingRecursiveComparison()
                .comparingOnlyFields("comment", "dateInsert", "book")
                .isEqualTo(expectedList);
    }

    @DisplayName("возвращать ожидаемый список коментариев за дату")
    @Test
    void shouldReturnExpectedBookCommentListByDate() {
        Book book = bookService.createBook(BOOK_TITLE, BOOK_AUTHOR_NAME, BOOK_GENRE_NAME);
        Date dateIns = Utils.dateStrFormat("12.12.2021");
        Date dateIns2 = Utils.dateStrFormat("13.12.2021");
        bookCommentService.createBookComment(BOOK_TITLE, BOOK_COMMENT, dateIns);
        bookCommentService.createBookComment(BOOK_TITLE, BOOK_COMMENT2, dateIns2);

        List<BookComment> actualList = bookCommentService.getAllCommentsByDate(dateIns);

        List<BookComment> expectedList = new ArrayList<>();
        expectedList.add(getBookComment(book, BOOK_COMMENT, dateIns));

        assertThat(actualList)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .comparingOnlyFields("comment", "dateInsert", "book")
                .isEqualTo(expectedList);
    }

    @DisplayName("возвращать ожидаемый список коментариев по книге")
    @Test
    void shouldReturnExpectedBookCommentListByBook() {
        Book book = bookService.createBook(BOOK_TITLE, BOOK_AUTHOR_NAME, BOOK_GENRE_NAME);
        Date dateIns = new Date();
        List<BookComment> lst = new ArrayList<>();
        lst.add(bookCommentService.createBookComment(BOOK_TITLE, BOOK_COMMENT, dateIns));
        lst.add(bookCommentService.createBookComment(BOOK_TITLE, BOOK_COMMENT2, dateIns));
        book.setComments(new HashSet<>(lst));

        List<BookComment> actualList = bookCommentService.getAllCommentsByBookTitle(BOOK_TITLE);

        List<BookComment> expectedList = new ArrayList<>();
        expectedList.add(getBookComment(book, BOOK_COMMENT, dateIns));
        expectedList.add(getBookComment(book, BOOK_COMMENT2, dateIns));

        assertThat(actualList)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .comparingOnlyFields("comment", "dateInsert", "book")
                .isEqualTo(expectedList);
    }

    @DisplayName("добавлять коментарий к книге")
    @Test
    void shouldCreateBookComment() {
        Book book = bookService.createBook(BOOK_TITLE, BOOK_AUTHOR_NAME, BOOK_GENRE_NAME);
        Date dateIns = new Date();
        bookCommentService.createBookComment(BOOK_TITLE, BOOK_COMMENT, dateIns);

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
        bookService.createBook(BOOK_TITLE, BOOK_AUTHOR_NAME, BOOK_GENRE_NAME);
        BookComment bc = bookCommentService.createBookComment(BOOK_TITLE, BOOK_COMMENT, new Date());
        bookCommentService.createBookComment(BOOK_TITLE, BOOK_COMMENT, new Date());
        bookCommentService.createBookComment(BOOK_TITLE, BOOK_COMMENT, new Date());

        bookCommentService.delBookCommentById(bc.getId());

        assertThat(bookCommentService.getBookCommentsCount()).isEqualTo(2);
    }

    @DisplayName("удалять все коментарии к книге")
    @Test
    void shouldDeleteAllBookCommentsByTitle() {
        Book book = bookService.createBook(BOOK_TITLE, BOOK_AUTHOR_NAME, BOOK_GENRE_NAME);
        List<BookComment> lst = new ArrayList<>();
        lst.add(bookCommentService.createBookComment(BOOK_TITLE, BOOK_COMMENT, new Date()));
        lst.add(bookCommentService.createBookComment(BOOK_TITLE, BOOK_COMMENT2, new Date()));
        book.setComments(new HashSet<>(lst));

        bookCommentService.delBookCommentsByBookTitle(BOOK_TITLE);
        em.flush();

        assertThat(bookCommentService.getBookCommentsCount()).isEqualTo(0);
    }

    @DisplayName("менять коментарий по id")
    @Test
    void shouldChangeBookCommentById() {
        Book book = bookService.createBook(BOOK_TITLE, BOOK_AUTHOR_NAME, BOOK_GENRE_NAME);
        Date dateIns = new Date();
        BookComment bcActual = bookCommentService.createBookComment(BOOK_TITLE, BOOK_COMMENT, dateIns);
        bookCommentService.changeCommentById(bcActual.getId(), BOOK_COMMENT2);

        BookComment bcExpected = getBookComment(book, BOOK_COMMENT2, dateIns);

        assertThat(bcActual)
                .usingRecursiveComparison()
                .comparingOnlyFields("comment", "dateInsert", "book")
                .isEqualTo(bcExpected);
    }
}
