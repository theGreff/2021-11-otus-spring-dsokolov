package ru.otus.dsokolov.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dsokolov.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbc;

    public GenreDaoJdbc(JdbcOperations jdbc, NamedParameterJdbcOperations namedParameterJdbc) {
        this.jdbc = jdbc;
        this.namedParameterJdbc = namedParameterJdbc;
    }

    @Override
    public Genre getById(long id) {
        if (id < 1) {
            return null;
        }

        return namedParameterJdbc.queryForObject("select id, name from genre where id = :id",
                Collections.singletonMap("id", id), new GenreMapper());
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
