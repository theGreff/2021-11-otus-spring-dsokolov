package ru.otus.dsokolov.service;

import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.dto.BookDto;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book getBookByTitle(String bookTitle);

    Book getById(long id);

    long getCount();

    void deleteBook(long id);

    Book createBook(BookDto bookDto);

    void update(BookDto bookDto);
}
