package ru.otus.dsokolov.dto;

import ru.otus.dsokolov.domain.Author;
import ru.otus.dsokolov.domain.Genre;

public class BookDto {

    private long id;

    private String title;

    private Author author;

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
