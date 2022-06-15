package ru.otus.dsokolov.service;

import org.springframework.stereotype.Component;
import ru.otus.dsokolov.domain.Author;
import ru.otus.dsokolov.repository.AuthorRepository;

import java.text.MessageFormat;
import java.util.List;

@Component
public class AuthorServiceImpl implements AuthorService {

    private final static String ERR_MSG = "Error! Author was not found by {0} = {1}";

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getById(long id) {
        return authorRepository.getById(id).orElseThrow(() -> new RuntimeException(MessageFormat.format(ERR_MSG, "id", id)));
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }
}
