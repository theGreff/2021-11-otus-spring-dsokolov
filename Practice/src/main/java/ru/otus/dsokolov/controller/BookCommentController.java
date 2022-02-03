package ru.otus.dsokolov.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.dsokolov.base.ApplicationException;
import ru.otus.dsokolov.domain.Book;
import ru.otus.dsokolov.domain.BookComment;
import ru.otus.dsokolov.dto.BookCommentDto;
import ru.otus.dsokolov.service.BookCommentService;
import ru.otus.dsokolov.service.BookService;

import java.util.Date;
import java.util.Set;

@Controller
public class BookCommentController {

    private final BookService bookService;
    private final BookCommentService bookCommentService;

    public BookCommentController(BookService bookService, BookCommentService bookCommentService) {
        this.bookService = bookService;
        this.bookCommentService = bookCommentService;
    }

    @GetMapping("/book-comment-all")
    public String bookCommentListView(@RequestParam("bookId") long bookId, Model model) throws ApplicationException {
        try {
            Book book = bookService.getById(bookId);
            Set<BookComment> bookCommentList = book.getComments();
            model.addAttribute("bookComments", bookCommentList);
            model.addAttribute("book", book);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

        return "bookCommentList";
    }

    @GetMapping("/book-comment-add")
    public String addBookCommentPage(@RequestParam("bookId") long bookId, Model model) {
        BookCommentDto bookCommentDto = new BookCommentDto();
        bookCommentDto.setBookId(bookId);
        model.addAttribute("bookComment", bookCommentDto);

        return "bookCommentNew";
    }

    @PostMapping("/book-comment-add")
    public String addBookComment(@ModelAttribute("bookComment") BookCommentDto bookCommentDto) throws ApplicationException {
        try {
            bookCommentDto.setBook(bookService.getById(bookCommentDto.getBookId()));
            bookCommentDto.setComment(bookCommentDto.getComment());
            bookCommentDto.setDateInsert(new Date());

            bookCommentService.createBookComment(bookCommentDto);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

        return "redirect:/book-comment-all?bookId=" + bookCommentDto.getBookId();
    }

    @GetMapping("/book-comment-del")
    public String delBookCommentPage(@RequestParam("id") long id, Model model) throws ApplicationException {
        try {
            BookComment bookComment = bookCommentService.getCommentById(id);
            BookCommentDto bookCommentDto = new BookCommentDto(id, bookComment.getComment(),
                    bookComment.getDateInsert(), bookComment.getBook());
            model.addAttribute("bookComment", bookCommentDto);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

        return "bookCommentDelete";
    }

    @PostMapping("/book-comment-del")
    public String delBookComment(@ModelAttribute("bookComment") BookCommentDto bookCommentDto) throws ApplicationException {
        try {
            bookCommentService.delBookCommentById(bookCommentDto.getId());

            return "redirect:/book-comment-all?bookId=" + bookCommentDto.getBookId();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handleException(ApplicationException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }
}
