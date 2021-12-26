package ru.otus.dsokolov.service;

import ru.otus.dsokolov.domain.BookComment;

import java.util.Date;
import java.util.List;

interface BookCommentService {

    int getBookCommentsCount();

    List<BookComment> getAllComments();

    List<BookComment> getAllCommentsByDate(Date date);

    BookComment createBookComment(String bookTitle, String comment);

    void delBookCommentsByBookTitle(String bookTitle);

    void delBookCommentsById(long id);

    void changeCommentById(long id, String commentNew);
}
