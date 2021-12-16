package ru.otus.dsokolov.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dsokolov.domain.Book;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbs implements BookDao {

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbc;

    public BookDaoJdbs(JdbcOperations jdbc, NamedParameterJdbcOperations namedParameterJdbc) {
        this.jdbc = jdbc;
        this.namedParameterJdbc = namedParameterJdbc;
    }

    @Override
    public int count() {
        Integer count = jdbc.queryForObject("select count(*) from book", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public Book getById(long id) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public void insert(Book book) {
        Map values = new HashMap();
        values.put("id", book.getId());
        values.put("title", book.getTitle());
        values.put("idauthor", book.getAuthor() != null ? book.getAuthor().getId() : null);
        values.put("idgenre", book.getGenre() != null ? book.getId() : null);

        namedParameterJdbc.update("insert into book (title, idauthor, idgenre) values (:title, :idauthor, :idgenre)",
                values);
    }

    @Override
    public void update(Book book) {
        namedParameterJdbc.update("update book set title = :title, idauthor = :idauthor" +
                        ", idgenre = :idgenre where id = :id",
                Map.of("id", book.getId(), "title", book.getTitle(),
                        "idauthor", book.getAuthor().getId(), "idgenre", book.getGenre().getId()));
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbc.update("delete from book where id = :id", Collections.singletonMap("id", id));
    }
}
