package ru.otus.dsokolov.dao;

import ru.otus.dsokolov.domain.Book;

import java.util.List;

public interface BookDao {

    int count();

    Book getById(long id);

    List<Book> getAll();

    void insert(Book book);

    void update(Book book);

    void deleteById(long id);
}
