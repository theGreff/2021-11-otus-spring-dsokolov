package ru.otus.dsokolov.service;

import org.springframework.stereotype.Component;
import ru.otus.dsokolov.domain.Genre;
import ru.otus.dsokolov.repository.GenreRepository;

import java.text.MessageFormat;

@Component
public class GenreServiceImpl implements GenreService {

    private final static String ERR_MSG = "Error! Genre was not found by {0} = {1}";

    private final GenreRepository genreRepository;

    public GenreServiceImpl(final GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre getById(long id) {
        return genreRepository.getById(id).orElseThrow(() -> new RuntimeException(MessageFormat.format(ERR_MSG, "id", id)));
    }

    @Override
    public Genre getByName(String name) {
        return genreRepository.getByName(name).orElseThrow(() -> new RuntimeException(MessageFormat.format(ERR_MSG, "name", name)));
    }
}
