package ru.otus.dsokolov.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.dsokolov.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public BookMapper(AuthorDao authorDao, GenreDao genreDao) {
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("title");
        long idAuthor = resultSet.getLong("idAuthor");
        long idGenre = resultSet.getLong("idGenre");

        return new Book(id, name, authorDao.getById(idAuthor), genreDao.getById(idGenre));
    }
}
