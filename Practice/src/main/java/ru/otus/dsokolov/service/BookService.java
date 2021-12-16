package ru.otus.dsokolov.service;

import org.springframework.stereotype.Component;
import ru.otus.dsokolov.dao.AuthorDao;
import ru.otus.dsokolov.dao.BookDao;
import ru.otus.dsokolov.dao.GenreDao;
import ru.otus.dsokolov.domain.Author;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.domain.Genre;

import java.util.List;

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

    public void deleteBook(String title) {
        bookDao.deleteById(bookDao.getByTitle(title).getId());
    }

    public void changeTitle(String title, String titleNew) {
        Book book = bookDao.getByTitle(title);
        book.setTitle(titleNew);

        bookDao.update(book);
    }

    public void changeAuthor(String bookTitle, String authorFullNameNew) {
        Book book = bookDao.getByTitle(bookTitle);
        Author author = authorDao.getByName(authorFullNameNew);
        book.setAuthor(author);

        bookDao.update(book);
    }

    public void changeGenre(String bookTitle, String genreNameNew) {
        Book book = bookDao.getByTitle(bookTitle);
        Genre genre = genreDao.getByName(genreNameNew);
        book.setGenre(genre);

        bookDao.update(book);
    }

    public int getBooksCount() {
        return bookDao.getCount();
    }

    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    public Book getBookByTitle(String bookTitle) {
        return bookDao.getByTitle(bookTitle);
    }
}
