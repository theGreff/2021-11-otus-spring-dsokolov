package ru.otus.dsokolov.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.dsokolov.domain.Author;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        long idAuthor = resultSet.getLong("idAuthor");
        String fullName = resultSet.getString("fullName");
        long idGenre = resultSet.getLong("idGenre");
        String name = resultSet.getString("name");

        return new Book(id, title, new Author(idAuthor, fullName), new Genre(idGenre, name));
    }
}
