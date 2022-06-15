package ru.otus.dsokolov.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dsokolov.base.ApplicationException;
import ru.otus.dsokolov.domain.BookComment;
import ru.otus.dsokolov.repository.BookCommentRepository;
import ru.otus.dsokolov.rest.dto.BookCommentDto;

import java.text.MessageFormat;
import java.util.List;

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
    public long getBookCommentsCount() {
        return bookCommentRepository.count();
    }

    @Override
    public BookComment getCommentById(long id) {
        return bookCommentRepository.getById(id).orElseThrow(() ->
                new RuntimeException(MessageFormat.format(ERR_MSG_BOOK_COMMENT_NOT_FOUND, "id", id)));
    }

    @Override
    public List<BookComment> getAllComments() {
        return bookCommentRepository.findAll();
    }

    @Override
    @Transactional
    public BookComment createBookComment(BookCommentDto bookCommentDto) throws ApplicationException {
        BookComment bc = new BookComment();
        bc.setComment(bookCommentDto.getComment());
        bc.setBook(bookService.getById(bookCommentDto.getBookId()));
        bc.setDateInsert(bookCommentDto.getDateInsert());
        bookCommentRepository.save(bc);

        return bc;
    }

    @Transactional
    public void delBookCommentById(long id) {
        bookCommentRepository.delete(getCommentById(id));
    }
}
