package ru.otus.dsokolov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.dsokolov.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getByLogin(String login);
}
