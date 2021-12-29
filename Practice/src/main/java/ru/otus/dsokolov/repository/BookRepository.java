package ru.otus.dsokolov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.dsokolov.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    long count();

    Optional<Book> getById(long id);

    Optional<Book> getByTitle(String title);

    List<Book> findAll();

    boolean existsByTitle(String title);

    Book save(Book book);

    void delete(Book book);
}
