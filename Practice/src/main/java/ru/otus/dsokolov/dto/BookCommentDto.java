package ru.otus.dsokolov.dto;

import ru.otus.dsokolov.domain.Book;

import java.util.Date;

public class BookCommentDto {

    private long id;

    private String comment;

    private Date dateInsert;

    private Book book;

    private long bookId;

    public BookCommentDto() {
    }

    public BookCommentDto(long id, String comment, Date dateInsert, Book book) {
        this.id = id;
        this.comment = comment;
        this.dateInsert = dateInsert;
        this.book = book;
        this.bookId = book.getId();
    }

    public BookCommentDto(String comment, Date dateInsert, Book book) {
        this.comment = comment;
        this.dateInsert = dateInsert;
        this.book = book;
        this.bookId = book.getId();
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDateInsert() {
        return dateInsert;
    }

    public void setDateInsert(Date dateInsert) {
        this.dateInsert = dateInsert;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
