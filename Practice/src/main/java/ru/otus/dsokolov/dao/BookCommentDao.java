package ru.otus.dsokolov.dao;

import ru.otus.dsokolov.domain.BookComment;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookCommentDao {

    Optional<BookComment> getById(long id);

    List<BookComment> getByDate(Date date);

    List<BookComment> getAll();

    BookComment save(BookComment comment);

    void delete(BookComment comment);
}
