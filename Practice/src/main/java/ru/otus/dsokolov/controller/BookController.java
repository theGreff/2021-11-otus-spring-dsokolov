package ru.otus.dsokolov.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.dsokolov.base.ApplicationException;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.dto.BookDto;
import ru.otus.dsokolov.service.AuthorService;
import ru.otus.dsokolov.service.BookService;
import ru.otus.dsokolov.service.GenreService;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookController(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping("/book-all")
    public String bookListView(Model model) {
        List<Book> bookList = bookService.findAll();
        model.addAttribute("books", bookList);

        return "booksList";
    }

    @GetMapping("/book-edit")
    public String editBookPage(@RequestParam("id") long id, Model model) throws ApplicationException {
        try {
            Book book = bookService.getById(id);
            model.addAttribute("book", book);
            model.addAttribute("authors", authorService.getAll());
            model.addAttribute("genres", genreService.getAll());
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

        return "bookEdit";
    }

    @PostMapping("/book-edit")
    public String saveBook(@ModelAttribute("book") BookDto bookDto) throws ApplicationException {
        try {
            bookService.update(bookDto);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

        return "redirect:/book-all";
    }

    @GetMapping("/book-new")
    public String newBookPage(Model model) {
        BookDto book = new BookDto();
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("genres", genreService.getAll());

        return "bookNew";
    }

    @PostMapping("/book-new")
    public String createBook(@ModelAttribute("book") BookDto bookDto) throws ApplicationException {
        try {
            bookService.createBook(bookDto);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

        return "redirect:/book-all";
    }

    @GetMapping("/book-del")
    public String delBookPage(@RequestParam("id") long id, Model model) throws ApplicationException {
        try {
            Book book = bookService.getById(id);
            model.addAttribute("book", book);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

        return "bookDelete";
    }

    @PostMapping("/book-del")
    public String delBook(@ModelAttribute("book") BookDto bookDto) throws ApplicationException {
        try {
            bookService.deleteBook(bookDto.getId());
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

        return "redirect:/book-all";
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handleException(ApplicationException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }
}
