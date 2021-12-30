package ru.otus.dsokolov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.dsokolov.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> getByTitle(String title);

    boolean existsByTitle(String title);
}
