package ru.otus.dsokolov.dao;

import ru.otus.dsokolov.domain.Book;

import java.util.List;

public class BookDaoJpa implements BookDao{
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Book getById(long id) {
        return null;
    }

    @Override
    public Book getByTitle(String title) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public boolean isBookExist(String title) {
        return false;
    }

    @Override
    public void insert(Book book) {

    }

    @Override
    public void update(Book book) {

    }

    @Override
    public void deleteById(long id) {

    }
}
