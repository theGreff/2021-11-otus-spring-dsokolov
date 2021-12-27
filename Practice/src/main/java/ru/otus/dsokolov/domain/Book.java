package ru.otus.dsokolov.domain;

import javax.persistence.*;
import java.util.Set;

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
    @JoinColumn(name = "ID_AUTHOR")
    private Author author;

    @OneToOne(targetEntity = Genre.class)
    @JoinColumn(name = "ID_GENRE")
    private Genre genre;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<BookComment> comments;

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

    public Set<BookComment> getComments() {
        return comments;
    }

    public void setComments(Set<BookComment> comments) {
        this.comments = comments;
    }
}
