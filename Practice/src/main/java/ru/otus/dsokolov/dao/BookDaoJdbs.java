package ru.otus.dsokolov.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dsokolov.domain.Book;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbs implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbc;
    private final BookMapper bookMapper;

    private final static String ERR_MSG = "Error! Book was not found by {0} = {1}";

    public BookDaoJdbs(NamedParameterJdbcOperations namedParameterJdbc) {
        this.namedParameterJdbc = namedParameterJdbc;
        this.bookMapper = new BookMapper();
    }

    @Override
    public int getCount() {
        Integer count = namedParameterJdbc.queryForObject("select count(*) from book", Collections.emptyMap(), Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public Book getById(long id) {
        try {
            return namedParameterJdbc.queryForObject("select b.id, b.title, a.id as idauthor, a.fullname, \n" +
                            "g.id as idgenre, g.name from book b \n" +
                            "inner join author a on a.id = b.idauthor \n" +
                            "inner join genre g on b.idgenre = g.id \n" +
                            "where b.id = :id",
                    Collections.singletonMap("id", id), bookMapper);
        } catch (EmptyResultDataAccessException ex) {
            throw new RuntimeException(MessageFormat.format(ERR_MSG, "id", id));
        }
    }

    @Override
    public Book getByTitle(String title) {
        try {
            return namedParameterJdbc.queryForObject("select b.id, b.title, a.id as idauthor, a.fullname, \n" +
                            "g.id as idgenre, g.name from book b \n" +
                            "inner join author a on a.id = b.idauthor \n" +
                            "inner join genre g on b.idgenre = g.id \n" +
                            "where b.title = :title",
                    Collections.singletonMap("title", title), bookMapper);
        } catch (EmptyResultDataAccessException ex) {
            throw new RuntimeException(MessageFormat.format(ERR_MSG, "title", title));
        }
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbc.query("select b.id, b.title, a.id as idauthor, a.fullname, \n" +
                "g.id as idgenre, g.name from book b \n" +
                "inner join author a on a.id = b.idauthor \n" +
                "inner join genre g on b.idgenre = g.id", bookMapper);
    }

    @Override
    public boolean isBookExist(String title) {
        Integer count = namedParameterJdbc.queryForObject("select count(*) from book where title = :title",
                Map.of("title", title), Integer.class);
        return count != 0;
    }

    @Override
    public void insert(Book book) {
        namedParameterJdbc.update("insert into book (title, idauthor, idgenre) values (:title, :idauthor, :idgenre)",
                getValues(book));
    }

    @Override
    public void update(Book book) {
        namedParameterJdbc.update("update book set title = :title, idauthor = :idauthor" +
                ", idgenre = :idgenre where id = :id", getValues(book));
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbc.update("delete from book where id = :id", Collections.singletonMap("id", id));
    }

    private Map<String, java.io.Serializable> getValues(Book book) {
        Map<String, java.io.Serializable> values = new HashMap<>();
        values.put("id", book.getId());
        values.put("title", book.getTitle());
        values.put("idauthor", book.getAuthor() != null ? book.getAuthor().getId() : null);
        values.put("idgenre", book.getGenre() != null ? book.getGenre().getId() : null);

        return values;
    }
}
