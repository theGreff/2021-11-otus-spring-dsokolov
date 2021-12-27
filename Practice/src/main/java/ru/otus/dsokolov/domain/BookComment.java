package ru.otus.dsokolov.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BOOK_COMMENT")
public class BookComment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOOK_COMMENT")
    @SequenceGenerator(name = "SEQ_BOOK_COMMENT", sequenceName = "SEQ_BOOK_COMMENT", allocationSize = 1)
    private long id;

    @Column(name = "COMMENT", nullable = false)
    private String comment;

    @Column(name = "DATE_INSERT")
    private Date dateInsert;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_BOOK")
    private Book book;

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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getDateInsert() {
        return dateInsert;
    }

    public void setDateInsert(Date dateInsert) {
        this.dateInsert = dateInsert;
    }
}
