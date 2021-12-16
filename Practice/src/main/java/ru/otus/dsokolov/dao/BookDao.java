package ru.otus.dsokolov.dao;

import ru.otus.dsokolov.domain.Book;

import java.util.List;

public interface BookDao {

    int getCount();

    Book getById(long id);

    Book getByTitle(String title);

    List<Book> getAll();

    void insert(Book book);

    void update(Book book);

    void deleteById(long id);
}
