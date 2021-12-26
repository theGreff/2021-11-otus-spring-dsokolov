package ru.otus.dsokolov.service;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.dsokolov.domain.BookComment;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

@ShellComponent
public class BookCommentShellService {
    private final BookCommentService bookCommentService;

    public BookCommentShellService(BookCommentService bookCommentService) {
        this.bookCommentService = bookCommentService;
    }

    @ShellMethod(key = "get-bc-cnt", value = "get all books comments count")
    public void getBookCommentsCount() {
        bookCommentService.getBookCommentsCount();
    }

    @ShellMethod(key = "get-bc-all", value = "get all books comments")
    public void getAllComments() {
        List<BookComment> bcList = bookCommentService.getAllComments();
        bcList.forEach(o ->
                System.out.println(MessageFormat.format("Title = {0}. Comment = {1}. Date = {2}",
                        o.getBook().getTitle(), o.getComment(), o.getDateInsert())));
    }

    @ShellMethod(key = "get-bc-date", value = "get book comments by date")
    public void getAllCommentsByDate(Date date) {
        List<BookComment> bcList = bookCommentService.getAllCommentsByDate(date);
        bcList.forEach(o ->
                System.out.println(MessageFormat.format("Title = {0}. Comment = {1}. Date = {2}",
                        o.getBook().getTitle(), o.getComment(), o.getDateInsert())));
    }

    @ShellMethod(key = "create-bc", value = "create book comment")
    public void createBookComment(@ShellOption String title, @ShellOption String comment) {
        BookComment bc = bookCommentService.createBookComment(title, comment);
        System.out.println(MessageFormat.format("Book comment was created with id = {0}", bc.getId()));
    }

    @ShellMethod(key = "del-bc-book", value = "delete book comment by book title")
    public void delBookCommentsByBookTitle(@ShellOption String bookTitle) {
        bookCommentService.delBookCommentsByBookTitle(bookTitle);
    }

    @ShellMethod(key = "del-bc-id", value = "delete book comment by id")
    public void delBookCommentsByBookTitle(@ShellOption long id) {
        bookCommentService.delBookCommentsById(id);
    }

    @ShellMethod(key = "change-bc-id", value = "change book comment by id")
    public void changeCommentById(@ShellOption long id, @ShellOption String commentNew) {
        bookCommentService.changeCommentById(id, commentNew);
    }
}
