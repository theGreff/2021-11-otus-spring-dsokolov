package ru.otus.dsokolov.service;

import ru.otus.dsokolov.base.ApplicationException;
import ru.otus.dsokolov.domain.BookComment;
import ru.otus.dsokolov.rest.dto.BookCommentDto;

import java.util.List;

public interface BookCommentService {

    long getBookCommentsCount();

    BookComment getCommentById(long id);

    List<BookComment> getAllComments();

    BookComment createBookComment(BookCommentDto bookCommentDto) throws ApplicationException;

    void delBookCommentById(long id);
}
