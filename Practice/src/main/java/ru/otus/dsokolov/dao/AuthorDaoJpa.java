package ru.otus.dsokolov.dao;

import org.springframework.stereotype.Component;
import ru.otus.dsokolov.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private final EntityManager em;

    public AuthorDaoJpa(final EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Author> getById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public Optional<Author> getByName(String name) {
        List<Author> authorList = em.createQuery("select a from Author a where a.fullName = :name", Author.class)
                .setParameter("name", name)
                .getResultList();
        if (authorList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(authorList.iterator().next());
    }
}
