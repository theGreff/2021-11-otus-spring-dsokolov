package ru.otus.dsokolov.dao;

import ru.otus.dsokolov.domain.Genre;

import java.util.Optional;

public interface GenreDao {

    Optional<Genre> getById(long id);

    Optional<Genre> getByName(String name);
}
