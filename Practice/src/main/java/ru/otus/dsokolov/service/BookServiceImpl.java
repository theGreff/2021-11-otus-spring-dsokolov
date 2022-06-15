package ru.otus.dsokolov.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dsokolov.base.ApplicationException;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.repository.BookRepository;
import ru.otus.dsokolov.rest.dto.BookDto;

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
    public void deleteBook(long id) throws ApplicationException {
        bookRepository.delete(getById(id));
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookByTitle(String bookTitle) throws ApplicationException {
        return bookRepository.getByTitle(bookTitle).orElseThrow(() ->
                new ApplicationException(MessageFormat.format(ERR_MSG_BOOK_NOT_FOUND, "title", bookTitle)));
    }

    @Override
    public Book getById(long id) throws ApplicationException {
        return bookRepository.findById(id).orElseThrow(() ->
                new ApplicationException(MessageFormat.format(ERR_MSG_BOOK_NOT_FOUND, "id", id)));
    }

    private void checkBookExist(String title) throws ApplicationException {
        if (bookRepository.existsByTitle(title)) {
            throw new ApplicationException(MessageFormat.format(ERR_MSG_BOOK_EXIST, title));
        }
    }

    @Override
    public long getCount() {
        return bookRepository.count();
    }

    @Override
    @Transactional
    public Book createBook(BookDto bookDto) throws ApplicationException {
        checkBookExist(bookDto.getTitle());

        Book bookNew = new Book();
        bookNew.setTitle(bookDto.getTitle());
        bookNew.setAuthor(bookDto.getAuthor());
        bookNew.setGenre(bookDto.getGenre());

        return bookRepository.save(bookNew);
    }

    @Override
    @Transactional
    public Book update(BookDto bookDto) throws ApplicationException {
        Book bookOld = bookRepository.getById(bookDto.getId());
        if (!bookDto.getTitle().equals(bookOld.getTitle())) {
            checkBookExist(bookDto.getTitle());
        }

        bookOld.setTitle(bookDto.getTitle());
        bookOld.setAuthor(bookDto.getAuthor());
        bookOld.setGenre(bookDto.getGenre());

        return bookRepository.save(bookOld);
    }
}
