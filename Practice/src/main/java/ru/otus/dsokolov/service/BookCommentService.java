package ru.otus.dsokolov.service;

import ru.otus.dsokolov.domain.BookComment;

import java.util.Date;
import java.util.List;

interface BookCommentService {

    int getBookCommentsCount();

    List<BookComment> getAllComments();

    List<BookComment> getAllCommentsByDate(Date date);

    BookComment createBookComment(String bookTitle, String comment);

    void delBookCommentsById(long id);

    void delBookCommentsByBookTitle(String title);

    void changeCommentById(long id, String commentNew);
}
