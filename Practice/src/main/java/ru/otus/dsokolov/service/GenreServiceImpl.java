package ru.otus.dsokolov.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dsokolov.dao.GenreDao;
import ru.otus.dsokolov.domain.Genre;

import java.text.MessageFormat;

@Component
public class GenreServiceImpl implements GenreService {

    private final static String ERR_MSG = "Error! Genre was not found by {0} = {1}";

    private final GenreDao genreDao;

    public GenreServiceImpl(final GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Genre getById(long id) {
        return genreDao.getById(id).orElseThrow(() -> new RuntimeException(MessageFormat.format(ERR_MSG, "id", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Genre getByName(String name) {
        return genreDao.getByName(name).orElseThrow(() -> new RuntimeException(MessageFormat.format(ERR_MSG, "name", name)));
    }
}
