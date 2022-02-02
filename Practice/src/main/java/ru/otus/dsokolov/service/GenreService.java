package ru.otus.dsokolov.service;

import ru.otus.dsokolov.domain.Genre;

import java.util.List;

public interface GenreService {

    Genre getById(long id);

    Genre getByName(String name);

    List<Genre> getAll();
}
