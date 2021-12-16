package ru.otus.dsokolov.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dsokolov.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbc;

    public AuthorDaoJdbc(JdbcOperations jdbc, NamedParameterJdbcOperations namedParameterJdbc) {
        this.jdbc = jdbc;
        this.namedParameterJdbc = namedParameterJdbc;
    }

    @Override
    public Author getById(long id) {
        if (id < 1) {
            return null;
        }

        return namedParameterJdbc.queryForObject("select id, fullName from author where id = :id",
                Collections.singletonMap("id", id), new AuthorMapper());
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
