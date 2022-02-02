package ru.otus.dsokolov.service;

import ru.otus.dsokolov.domain.Author;

import java.util.List;

public interface AuthorService {

    Author getById(long id);

    List<Author> getAll();
}
