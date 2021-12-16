package ru.otus.dsokolov.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dsokolov.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Collections;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbc;

    private final static String errMsg = "Genre was not found by {0} = {1}";

    public GenreDaoJdbc(final NamedParameterJdbcOperations namedParameterJdbc) {
        this.namedParameterJdbc = namedParameterJdbc;
    }

    @Override
    public Genre getById(long id) {
        if (id < 1) {
            return null;
        }

        try {
            return namedParameterJdbc.queryForObject("select id, name from genre where id = :id",
                    Collections.singletonMap("id", id), new GenreMapper());
        } catch (EmptyResultDataAccessException ex) {
            throw new RuntimeException(MessageFormat.format(errMsg, "id", id));
        }
    }

    @Override
    public Genre getByName(String name) {
        try {
            return namedParameterJdbc.queryForObject("select id, name from genre where name = :name",
                    Collections.singletonMap("name", name), new GenreMapper());
        } catch (EmptyResultDataAccessException ex) {
            throw new RuntimeException(MessageFormat.format(errMsg, "name", name));
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");

            return new Genre(id, name);
        }
    }
}
