package ru.otus.dsokolov.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dsokolov.dao.BookCommentDao;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.domain.BookComment;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

@Component
public class BookCommentServiceImpl implements BookCommentService {

    private final static String ERR_MSG_BOOK_COMMENT_NOT_FOUND = "Error! Book comment was not found by {0} = {1}";

    private final BookService bookService;

    private final BookCommentDao bookCommentDao;

    public BookCommentServiceImpl(final BookService bookService, final BookCommentDao bookCommentDao) {
        this.bookService = bookService;
        this.bookCommentDao = bookCommentDao;
    }

    @Override
    @Transactional(readOnly = true)
    public int getBookCommentsCount() {
        return 0;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookComment> getAllComments() {
        return bookCommentDao.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookComment> getAllCommentsByDate(Date date) {
        return bookCommentDao.getByDate(date);
    }

    @Override
    @Transactional
    public BookComment createBookComment(String title, String comment) {
        Book book = bookService.getBookByTitle(title);
        BookComment bc = new BookComment();
        bc.setComment(comment);
        bc.setBook(book);
        bc.setDateInsert(new Date());
        bookCommentDao.save(bc);

        return bc;
    }

    @Override
    @Transactional
    public void delBookCommentsByBookTitle(String bookTitle) {
        Book book = bookService.getBookByTitle(bookTitle);
        book.getComments().forEach(bookCommentDao::delete);
    }

    @Transactional
    public void delBookCommentsById(long id) {
        bookCommentDao.delete(bookCommentDao.getById(id).get());
    }

    @Override
    @Transactional
    public void changeCommentById(long id, String commentNew) {
        BookComment bc = bookCommentDao.getById(id).orElseThrow(() ->
                new RuntimeException(MessageFormat.format(ERR_MSG_BOOK_COMMENT_NOT_FOUND, "id", id)));
        bc.setComment(commentNew);

        bookCommentDao.save(bc);
    }
}
