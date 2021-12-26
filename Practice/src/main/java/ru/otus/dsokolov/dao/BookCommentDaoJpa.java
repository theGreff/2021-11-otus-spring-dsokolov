package ru.otus.dsokolov.dao;

import org.springframework.stereotype.Component;
import ru.otus.dsokolov.domain.BookComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class BookCommentDaoJpa implements BookCommentDao {

    @PersistenceContext
    private final EntityManager em;

    public BookCommentDaoJpa(final EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<BookComment> getById(long id) {
        return Optional.ofNullable(em.find(BookComment.class, id));
    }

    @Override
    public List<BookComment> getByDate(Date date) {
        return em.createQuery("select bc from BookComment bc join fetch bc.book where dateInsert = :dateInsert",
                        BookComment.class)
                .setParameter("dateInsert", date)
                .getResultList();
    }

    @Override
    public List<BookComment> getAll() {
        return em.createQuery("select bc from BookComment bc join fetch bc.book", BookComment.class).getResultList();
    }

    @Override
    public BookComment save(BookComment comment) {
        if (comment.getId() <= 0) {
            em.persist(comment);

            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public void delete(BookComment comment) {
        em.remove(comment);
    }
}
