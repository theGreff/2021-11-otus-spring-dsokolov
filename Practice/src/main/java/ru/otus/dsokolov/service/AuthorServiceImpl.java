package ru.otus.dsokolov.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dsokolov.dao.AuthorDao;
import ru.otus.dsokolov.domain.Author;

import java.text.MessageFormat;

@Component
public class AuthorServiceImpl implements AuthorService {

    private final static String ERR_MSG = "Error! Author was not found by {0} = {1}";

    private final AuthorDao authorDao;

    public AuthorServiceImpl(final AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Author getById(long id) {
        return authorDao.getById(id).orElseThrow(() -> new RuntimeException(MessageFormat.format(ERR_MSG, "id", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Author getByName(String name) {
        return authorDao.getByName(name).orElseThrow(() -> new RuntimeException(MessageFormat.format(ERR_MSG, "name", name)));
    }
}
