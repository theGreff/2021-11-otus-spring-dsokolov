package ru.otus.dsokolov.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.dsokolov.base.ApplicationException;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.rest.dto.BookDto;
import ru.otus.dsokolov.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(value = "books/all", method = RequestMethod.GET)
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAll().stream().map(BookDto::toDto)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "books/{id}", method = RequestMethod.GET)
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") long id) throws ApplicationException {
        return ResponseEntity.ok(BookDto.toDto(bookService.getById(id)));
    }

    @PostMapping("books")
    public ResponseEntity<BookDto> createNewBook(@RequestBody BookDto dto) throws ApplicationException {
        Book book = bookService.createBook(dto);

        return ResponseEntity.ok(BookDto.toDto(book));
    }

    @PatchMapping("/books/{id}/title")
    public ResponseEntity<BookDto> updateBookTitleById(@PathVariable("id") long id, @RequestParam("title") String title) throws ApplicationException {
        BookDto bookDto = BookDto.toDto(bookService.getById(id));
        bookDto.setTitle(title);
        bookService.update(bookDto);

        return ResponseEntity.ok(bookDto);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBookById(@PathVariable("id") long id) throws ApplicationException {
        bookService.deleteBook(id);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<String> handleApplicationException(ApplicationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
