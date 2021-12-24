package ru.otus.dsokolov.dao;

import org.springframework.stereotype.Component;
import ru.otus.dsokolov.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private final EntityManager em;

    public BookDaoJpa(final EntityManager em) {
        this.em = em;
    }

    @Override
    public int getCount() {
        return em.createQuery("select count(b) from Book b", Long.class).getSingleResult().intValue();
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public Optional<Book> getByTitle(String title) {
        List<Book> bookList = em.createQuery("select b from Book b join fetch b.author join fetch b.genre where b.title = :title", Book.class)
                .setParameter("title", title)
                .getResultList();
        if (bookList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(bookList.iterator().next());
    }

    @Override
    public List<Book> getAll() {
        return em.createQuery("select b from Book b join fetch b.author join fetch b.genre", Book.class).getResultList();
    }

    @Override
    public boolean isBookExist(String title) {
        Long result = em.createQuery("select count(b) from Book b where b.title = :title", Long.class)
                .setParameter("title", title)
                .getSingleResult();

        return result > 0L;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public void delete(Book book) {
        em.remove(book);
    }
}
