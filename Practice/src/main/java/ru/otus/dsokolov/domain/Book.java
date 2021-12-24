package ru.otus.dsokolov.domain;

import javax.persistence.*;

@Entity
@Table(name = "BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOOK")
    @SequenceGenerator(name = "SEQ_BOOK", sequenceName = "SEQ_BOOK", allocationSize = 1)
    private long id;

    @Column(name = "TITLE", nullable = false, unique = true)
    private String title;

    @OneToOne(targetEntity = Author.class)
    @JoinColumn(name = "IDAUTHOR")
    private Author author;

    @OneToOne(targetEntity = Genre.class)
    @JoinColumn(name = "IDGENRE")
    private Genre genre;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
