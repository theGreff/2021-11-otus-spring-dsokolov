package ru.otus.dsokolov.service;

import ru.otus.dsokolov.domain.BookComment;
import ru.otus.dsokolov.rest.dto.BookCommentDto;

import java.util.List;

public interface BookCommentService {

    long getBookCommentsCount();

    BookComment getCommentById(long id);

    List<BookComment> getAllComments();

    BookComment createBookComment(BookCommentDto bookCommentDto);

    void delBookCommentById(long id);
}
