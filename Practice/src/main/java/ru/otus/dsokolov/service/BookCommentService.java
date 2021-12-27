package ru.otus.dsokolov.service;

import ru.otus.dsokolov.domain.BookComment;

import java.util.Date;
import java.util.List;

interface BookCommentService {

    int getBookCommentsCount();

    BookComment getCommentById(long id);

    List<BookComment> getAllComments();

    List<BookComment> getAllCommentsByDate(Date date);

    List<BookComment> getAllCommentsByBookTitle(String bookTitle);

    BookComment createBookComment(String bookTitle, String comment, Date dateInsert);

    void delBookCommentById(long id);

    void delBookCommentsByBookTitle(String title);

    void changeCommentById(long id, String commentNew);
}
