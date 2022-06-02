package ru.otus.dsokolov.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dsokolov.repository.BookRepository;
import ru.otus.dsokolov.rest.dto.BookDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/api/books")
    public List<BookDto> getAllPersons() {
        return bookRepository.findAll().stream().map(BookDto::toDto)
                .collect(Collectors.toList());
    }
}
