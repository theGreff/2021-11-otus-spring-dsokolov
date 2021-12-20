package ru.otus.dsokolov.service;

import org.springframework.stereotype.Component;
import ru.otus.dsokolov.dao.AuthorDao;
import ru.otus.dsokolov.dao.BookDao;
import ru.otus.dsokolov.dao.GenreDao;
import ru.otus.dsokolov.domain.Author;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.domain.Genre;

import java.text.MessageFormat;
import java.util.List;

@Component
public class BookServiceImpl implements BookService {

    private static final String ERR_MSG = "Error! Book with title = {0} already exists";

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public BookServiceImpl(final BookDao bookDao, final AuthorDao authorDao, final GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public void createBook(String title, String authorName, String genreName) {
        Author author = authorDao.getByName(authorName);
        Genre genre = genreDao.getByName(genreName);

        if (bookDao.isBookExist(title)) {
            throw new RuntimeException(MessageFormat.format(ERR_MSG, title));
        }

        bookDao.insert(new Book(title, author, genre));
    }

    @Override
    public void deleteBook(String title) {
        bookDao.deleteById(bookDao.getByTitle(title).getId());
    }

    @Override
    public void changeTitle(String title, String titleNew) {
        if (bookDao.isBookExist(titleNew)) {
            throw new RuntimeException(MessageFormat.format(ERR_MSG, titleNew));
        }
        Book book = bookDao.getByTitle(title);
        book.setTitle(titleNew);

        bookDao.update(book);
    }

    @Override
    public void changeAuthor(String bookTitle, String authorFullNameNew) {
        Book book = bookDao.getByTitle(bookTitle);
        Author author = authorDao.getByName(authorFullNameNew);
        book.setAuthor(author);

        bookDao.update(book);
    }

    @Override
    public void changeGenre(String bookTitle, String genreNameNew) {
        Book book = bookDao.getByTitle(bookTitle);
        Genre genre = genreDao.getByName(genreNameNew);
        book.setGenre(genre);

        bookDao.update(book);
    }

    @Override
    public int getBooksCount() {
        return bookDao.getCount();
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    @Override
    public Book getBookByTitle(String bookTitle) {
        return bookDao.getByTitle(bookTitle);
    }
}
