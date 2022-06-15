package ru.otus.dsokolov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.dsokolov.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> getById(long id);

    Optional<Author> getByFullName(String name);
}
