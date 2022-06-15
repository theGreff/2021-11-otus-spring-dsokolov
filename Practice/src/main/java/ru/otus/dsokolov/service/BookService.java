package ru.otus.dsokolov.service;

import ru.otus.dsokolov.base.ApplicationException;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.rest.dto.BookDto;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book getBookByTitle(String bookTitle) throws ApplicationException;

    Book getById(long id) throws ApplicationException;

    long getCount();

    void deleteBook(long id) throws ApplicationException;

    Book createBook(BookDto bookDto) throws ApplicationException;

    Book update(BookDto bookDto) throws ApplicationException;
}
