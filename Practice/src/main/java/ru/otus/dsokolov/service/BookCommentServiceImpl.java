package ru.otus.dsokolov.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.domain.BookComment;
import ru.otus.dsokolov.repository.BookCommentRepository;

import java.text.MessageFormat;
import java.util.*;

@Component
public class BookCommentServiceImpl implements BookCommentService {

    private final static String ERR_MSG_BOOK_COMMENT_NOT_FOUND = "Error! Book comment was not found by {0} = {1}";

    private final BookService bookService;
    private final BookCommentRepository bookCommentRepository;

    public BookCommentServiceImpl(final BookService bookService, final BookCommentRepository bookCommentRepository) {
        this.bookService = bookService;
        this.bookCommentRepository = bookCommentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public long getBookCommentsCount() {
        return bookCommentRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public BookComment getCommentById(long id) {
        return bookCommentRepository.getById(id).orElseThrow(() ->
                new RuntimeException(MessageFormat.format(ERR_MSG_BOOK_COMMENT_NOT_FOUND, "id", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookComment> getAllComments() {
        return bookCommentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookComment> getAllCommentsByDate(Date date) {
        return bookCommentRepository.getByDateInsert(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookComment> getAllCommentsByBookTitle(String bookTitle) {
        Book book = bookService.getBookByTitle(bookTitle);
        Set<BookComment> bookComments = book.getComments();
        if (bookComments == null) {
            return Collections.emptyList();
        }

        return new ArrayList<>(bookComments);
    }

    @Override
    @Transactional
    public BookComment createBookComment(String title, String comment, Date dateInsert) {
        Book book = bookService.getBookByTitle(title);

        BookComment bc = new BookComment();
        bc.setComment(comment);
        bc.setBook(book);
        bc.setDateInsert(dateInsert);
        bookCommentRepository.save(bc);

        return bc;
    }

    @Transactional
    public void delBookCommentById(long id) {
        bookCommentRepository.delete(getCommentById(id));
    }

    @Override
    @Transactional
    public void delBookCommentsByBookTitle(String title) {
        Book book = bookService.getBookByTitle(title);

        Set<BookComment> bcList = book.getComments();
        for (BookComment bc : bcList) {
            bookCommentRepository.delete(bc);
        }
    }

    @Override
    @Transactional
    public void changeCommentById(long id, String commentNew) {
        BookComment bc = getCommentById(id);
        bc.setComment(commentNew);

        bookCommentRepository.save(bc);
    }
}
