package ru.otus.dsokolov.dao;

import ru.otus.dsokolov.domain.Author;
import ru.otus.dsokolov.domain.Genre;

public interface GenreDao {

    Genre getById(long id);

    Genre getByName(String name);
}
