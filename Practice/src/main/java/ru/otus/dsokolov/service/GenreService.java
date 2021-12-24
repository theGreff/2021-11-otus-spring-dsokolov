package ru.otus.dsokolov.service;

import ru.otus.dsokolov.domain.Genre;

public interface GenreService {

    Genre getById(long id);

    Genre getByName(String name);
}
