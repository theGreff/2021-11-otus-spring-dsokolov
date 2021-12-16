package ru.otus.dsokolov.dao;

import ru.otus.dsokolov.domain.Author;

public interface AuthorDao {

    Author getById(long id);

    Author getByName(String name);
}
