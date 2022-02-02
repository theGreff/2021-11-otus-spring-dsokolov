package ru.otus.dsokolov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String editBookPage(@RequestParam("id") long id, Model model) {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("genres", genreService.getAll());

        return "bookEdit";
    }

    @PostMapping("/book-edit")
    public String saveBook(@ModelAttribute("book") BookDto bookDto) {
        bookService.update(bookDto);

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
    public String createBook(@ModelAttribute("book") BookDto bookDto) {
        bookService.createBook(bookDto);

        return "redirect:/book-all";
    }

    @GetMapping("/book-del")
    public String delBookPage(@RequestParam("id") long id, Model model) {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);

        return "bookDelete";
    }

    @PostMapping("/book-del")
    public String delBook(@ModelAttribute("book") BookDto bookDto) {
        bookService.deleteBook(bookDto.getTitle());

        return "redirect:/book-all";
    }
}
