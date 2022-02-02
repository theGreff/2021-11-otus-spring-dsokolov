package ru.otus.dsokolov.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dsokolov.domain.Author;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.domain.Genre;
import ru.otus.dsokolov.dto.BookDto;
import ru.otus.dsokolov.repository.BookRepository;

import java.text.MessageFormat;
import java.util.List;

@Component
public class BookServiceImpl implements BookService {

    private static final String ERR_MSG_BOOK_EXIST = "Error! Book with title = {0} already exists";
    private final static String ERR_MSG_BOOK_NOT_FOUND = "Error! Book was not found by {0} = {1}";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceImpl(final BookRepository bookRepository, final AuthorService authorService, final GenreService genreService) {
        this.bookRepository = bookRepository;
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

        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(String title) {
        bookRepository.delete(getBookByTitle(title));
    }

    @Override
    @Transactional
    public void changeTitle(String title, String titleNew) {
        checkBookExist(titleNew);
        Book book = getBookByTitle(title);
        book.setTitle(titleNew);

        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void changeAuthor(String bookTitle, String authorFullNameNew) {
        Book book = getBookByTitle(bookTitle);
        Author author = authorService.getByName(authorFullNameNew);
        book.setAuthor(author);

        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void changeGenre(String bookTitle, String genreNameNew) {
        Book book = getBookByTitle(bookTitle);
        Genre genre = genreService.getByName(genreNameNew);
        book.setGenre(genre);

        bookRepository.save(book);
    }

    @Override
    public long getCount() {
        return bookRepository.count();
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookByTitle(String bookTitle) {
        return bookRepository.getByTitle(bookTitle).orElseThrow(() ->
                new RuntimeException(MessageFormat.format(ERR_MSG_BOOK_NOT_FOUND, "title", bookTitle)));
    }

    @Override
    public Book getById(long id) {
        return bookRepository.getById(id);
    }

    private void checkBookExist(String title) {
        if (bookRepository.existsByTitle(title)) {
            throw new RuntimeException(MessageFormat.format(ERR_MSG_BOOK_EXIST, title));
        }
    }

    @Override
    @Transactional
    public Book createBook(BookDto bookDto) {
        checkBookExist(bookDto.getTitle());

        Book bookNew = new Book();
        bookNew.setTitle(bookDto.getTitle());
        bookNew.setAuthor(bookDto.getAuthor());
        bookNew.setGenre(bookDto.getGenre());

        return bookRepository.save(bookNew);
    }

    @Override
    @Transactional
    public void update(BookDto bookDto) {
        Book bookOld = bookRepository.getById(bookDto.getId());
        bookOld.setTitle(bookDto.getTitle());
        bookOld.setAuthor(bookDto.getAuthor());
        bookOld.setGenre(bookDto.getGenre());

        bookRepository.save(bookOld);
    }
}
