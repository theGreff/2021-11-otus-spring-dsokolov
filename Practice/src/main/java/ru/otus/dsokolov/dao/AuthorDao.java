package ru.otus.dsokolov.dao;

import ru.otus.dsokolov.domain.Author;

import java.util.Optional;

public interface AuthorDao {

    Optional<Author> getById(long id);

    Optional<Author> getByName(String name);
}
