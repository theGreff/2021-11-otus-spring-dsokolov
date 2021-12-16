package ru.otus.dsokolov.service;

import org.springframework.stereotype.Component;
import ru.otus.dsokolov.dao.AuthorDao;
import ru.otus.dsokolov.dao.BookDao;
import ru.otus.dsokolov.dao.GenreDao;
import ru.otus.dsokolov.domain.Author;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.domain.Genre;

@Component
public class BookService {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public BookService(final BookDao bookDao, final AuthorDao authorDao, final GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    public void createBook(String title, String authorName, String genreName) {
        Author author = authorDao.getByName(authorName);
        Genre genre = genreDao.getByName(genreName);

        bookDao.insert(new Book(title, author, genre));
    }
}
