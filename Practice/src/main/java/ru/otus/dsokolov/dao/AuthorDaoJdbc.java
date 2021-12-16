package ru.otus.dsokolov.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dsokolov.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Collections;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbc;

    private final static String errMsg = "Author was not found by {0} = {1}";

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbc) {
        this.namedParameterJdbc = namedParameterJdbc;
    }

    @Override
    public Author getById(long id) {
        if (id < 1) {
            return null;
        }

        try {
            return namedParameterJdbc.queryForObject("select id, fullName from author where id = :id",
                    Collections.singletonMap("id", id), new AuthorMapper());
        } catch (
                EmptyResultDataAccessException ex) {
            throw new RuntimeException(MessageFormat.format(errMsg, "id", id));
        }
    }

    @Override
    public Author getByName(String name) {
        try {
            return namedParameterJdbc.queryForObject("select id, fullName from author where fullName = :fullName",
                    Collections.singletonMap("fullName", name), new AuthorMapper());
        } catch (
                EmptyResultDataAccessException ex) {
            throw new RuntimeException(MessageFormat.format(errMsg, "fullName", name));
        }
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String fullName = resultSet.getString("fullName");

            return new Author(id, fullName);
        }
    }
}
