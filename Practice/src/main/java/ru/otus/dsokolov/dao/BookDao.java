package ru.otus.dsokolov.dao;

import ru.otus.dsokolov.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    int getCount();

    Optional<Book> getById(long id);

    Optional<Book> getByTitle(String title);

    List<Book> getAll();

    boolean isBookExist(String title);

    Book save(Book book);

    void delete(Book book);
}
