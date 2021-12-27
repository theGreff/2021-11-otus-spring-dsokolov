package ru.otus.dsokolov.service;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.dsokolov.base.Utils;
import ru.otus.dsokolov.domain.BookComment;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
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
        System.out.println(MessageFormat.format("Book count = {0}", bookCommentService.getBookCommentsCount()));
    }

    @ShellMethod(key = "get-bc-all", value = "get all comments of all books")
    public void getAllComments() {
        List<BookComment> bcList = bookCommentService.getAllComments();
        if (bcList.isEmpty()) {
            Utils.printEmptyResult();
            return;
        }

        bcList.forEach(this::printComment);
    }

    @ShellMethod(key = "get-bc-book", value = "get all book comments by book title")
    public void getAllCommentsByBook(String bookTitle) {
        List<BookComment> bcList = bookCommentService.getAllCommentsByBookTitle(bookTitle);
        if (bcList.isEmpty()) {
            Utils.printEmptyResult();
            return;
        }

        bcList.forEach(this::printComment);
    }

    @ShellMethod(key = "get-bc-date", value = "get book comments by date")
    public void getAllCommentsByDate(String date) {
        List<BookComment> bcList = bookCommentService.getAllCommentsByDate(Utils.dateStrFormat(date));
        if (bcList.isEmpty()) {
            Utils.printEmptyResult();
            return;
        }

        bcList.forEach(this::printComment);
    }

    @ShellMethod(key = "create-bc", value = "create book comment")
    public void createBookComment(@ShellOption String title, @ShellOption String comment) {
        BookComment bc = bookCommentService.createBookComment(title, comment, new Date());
        System.out.println(MessageFormat.format("The comment was created with id = {0}", bc.getId()));
    }

    @ShellMethod(key = "del-bc-id", value = "delete book comment by id")
    public void delBookCommentsByBookId(@ShellOption long id) {
        bookCommentService.delBookCommentById(id);

        System.out.println(MessageFormat.format("The comment with id = {0} was deleted", id));
    }

    @ShellMethod(key = "del-bc-book", value = "delete book comment by book title")
    public void delBookCommentsByBookTitle(@ShellOption String title) {
        bookCommentService.delBookCommentsByBookTitle(title);

        System.out.println(MessageFormat.format("All comments of book = {0} were deleted", title));
    }

    @ShellMethod(key = "change-bc-id", value = "change book comment by id")
    public void changeCommentById(@ShellOption long id, @ShellOption String commentNew) {
        bookCommentService.changeCommentById(id, commentNew);

        System.out.println(MessageFormat.format("The comment with id = {0} was changed", id));
    }

    private void printComment(BookComment comment) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("dd.MM.yyyy");
        String dateFormattedStr = sdf.format(comment.getDateInsert());

        System.out.println(MessageFormat.format("BookTitle = {0}. CommentID = {1}. Comment = {2}. Date = {3}",
                comment.getBook().getTitle(), comment.getId(), comment.getComment(), dateFormattedStr));
    }
}
