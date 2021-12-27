package ru.otus.dsokolov.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dsokolov.dao.BookDao;
import ru.otus.dsokolov.domain.Author;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.domain.Genre;

import java.text.MessageFormat;
import java.util.List;

@Component
public class BookServiceImpl implements BookService {

    private static final String ERR_MSG_BOOK_EXIST = "Error! Book with title = {0} already exists";
    private final static String ERR_MSG_BOOK_NOT_FOUND = "Error! Book was not found by {0} = {1}";

    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceImpl(final BookDao bookDao, final AuthorService authorService, final GenreService genreService) {
        this.bookDao = bookDao;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    @Transactional
    public Book createBook(String title, String authorName, String genreName) {
        checkBookExist(title);

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(authorService.getByName(authorName));
        book.setGenre(genreService.getByName(genreName));

        return bookDao.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(String title) {
        bookDao.delete(getBookByTitle(title));
    }

    @Override
    @Transactional
    public void changeTitle(String title, String titleNew) {
        checkBookExist(titleNew);
        Book book = getBookByTitle(title);
        book.setTitle(titleNew);

        bookDao.save(book);
    }

    @Override
    @Transactional
    public void changeAuthor(String bookTitle, String authorFullNameNew) {
        Book book = getBookByTitle(bookTitle);
        Author author = authorService.getByName(authorFullNameNew);
        book.setAuthor(author);

        bookDao.save(book);
    }

    @Override
    @Transactional
    public void changeGenre(String bookTitle, String genreNameNew) {
        Book book = getBookByTitle(bookTitle);
        Genre genre = genreService.getByName(genreNameNew);
        book.setGenre(genre);

        bookDao.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public int getBooksCount() {
        return bookDao.getCount();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookByTitle(String bookTitle) {
        return bookDao.getByTitle(bookTitle).orElseThrow(() ->
                new RuntimeException(MessageFormat.format(ERR_MSG_BOOK_NOT_FOUND, "title", bookTitle)));
    }

    private void checkBookExist(String title) {
        if (bookDao.isBookExist(title)) {
            throw new RuntimeException(MessageFormat.format(ERR_MSG_BOOK_EXIST, title));
        }
    }
}
