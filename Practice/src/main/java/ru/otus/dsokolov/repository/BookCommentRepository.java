package ru.otus.dsokolov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.dsokolov.domain.BookComment;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookCommentRepository extends JpaRepository<BookComment, Long> {

    long count();

    Optional<BookComment> getById(long id);

    List<BookComment> getByDateInsert(Date date);

    List<BookComment> findAll();

    BookComment save(BookComment comment);

    void delete(BookComment comment);
}
