package ru.otus.dsokolov.service;

import ru.otus.dsokolov.domain.Author;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.domain.Genre;
import ru.otus.dsokolov.dto.BookDto;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book getBookByTitle(String bookTitle);

    Book getById(long id);

    long getCount();

    Book createBook(String title, String authorName, String genreName);

    void deleteBook(String title);

    void changeTitle(String title, String titleNew);

    void changeAuthor(String bookTitle, String authorFullNameNew);

    void changeGenre(String bookTitle, String genreNameNew);

    Book createBook(BookDto bookDto);

    void update(BookDto bookDto);
}
