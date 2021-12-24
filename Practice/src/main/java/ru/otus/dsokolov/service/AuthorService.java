package ru.otus.dsokolov.service;

import ru.otus.dsokolov.domain.Author;

public interface AuthorService {

    Author getById(long id);

    Author getByName(String name);
}
