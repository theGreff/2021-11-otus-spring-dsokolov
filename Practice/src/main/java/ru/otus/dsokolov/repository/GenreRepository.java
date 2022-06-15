package ru.otus.dsokolov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.dsokolov.domain.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> getById(long id);

    Optional<Genre> getByName(String name);
}
