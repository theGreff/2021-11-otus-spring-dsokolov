package ru.otus.dsokolov.service;

import ru.otus.dsokolov.domain.Author;

import java.util.List;

public interface AuthorService {

    Author getById(long id);

    Author getByName(String name);

    List<Author> getAll();
}
