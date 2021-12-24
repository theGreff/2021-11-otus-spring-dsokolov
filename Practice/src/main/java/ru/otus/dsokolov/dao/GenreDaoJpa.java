package ru.otus.dsokolov.dao;

import org.springframework.stereotype.Component;
import ru.otus.dsokolov.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class GenreDaoJpa implements GenreDao {

    @PersistenceContext
    private final EntityManager em;

    public GenreDaoJpa(final EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Genre> getById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public Optional<Genre> getByName(String name) {
        List<Genre> genreList = em.createQuery("select a from Genre a where a.name = :name", Genre.class)
                .setParameter("name", name)
                .getResultList();
        if (genreList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(genreList.iterator().next());
    }
}
