package ru.otus.dsokolov.page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.dsokolov.base.ApplicationException;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.rest.dto.BookDto;
import ru.otus.dsokolov.service.AuthorService;
import ru.otus.dsokolov.service.BookService;
import ru.otus.dsokolov.service.GenreService;

@Controller
public class BookPageController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookPageController(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping("/book-all")
    public String bookListView(Model model) {
         return "bookList";
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

    @GetMapping("/book-add")
    public String newBookPage(Model model) {
        BookDto book = new BookDto();
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("genres", genreService.getAll());

        return "bookNew";
    }

    @PostMapping("/book-add")
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
