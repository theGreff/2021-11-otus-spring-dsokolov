package ru.otus.dsokolov.service;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.dsokolov.base.Utils;
import ru.otus.dsokolov.domain.Book;

import java.text.MessageFormat;
import java.util.List;

/*
 create 111 "Стивен Кинг" "ужасы"
 create-bc 111 111111111
 del-bc-book 111
*/

@ShellComponent
public class BookShellService {

    private final BookService bookService;

    public BookShellService(final BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(key = "create", value = "create new book")
    public void createBook(@ShellOption String title, @ShellOption String authorName, @ShellOption String genreName) {
        Book book = bookService.createBook(title, authorName, genreName);

        System.out.println(MessageFormat.format("Book was created with id = {0}", book.getId()));
    }

    @ShellMethod(key = "get-cnt", value = "get book count")
    public void getCount() {
        System.out.println(MessageFormat.format("Book count = {0}", bookService.getCount()));
    }

    @ShellMethod(key = "get-all", value = "get all book")
    public void getAll() {
        List<Book> bookList = bookService.findAll();
        if (bookList.isEmpty()) {
            Utils.printEmptyResult();
            return;
        }

        bookList.forEach(o -> System.out.println(MessageFormat.format("Title = {0}. Author = {1}. Genre = {2}",
                o.getTitle(), o.getAuthor().getFullName(), o.getGenre().getName())));
    }

    @ShellMethod(key = "find", value = "find book by title")
    public void findBookByTitle(@ShellOption String title) {
        Book book = bookService.getBookByTitle(title);

        System.out.println(MessageFormat.format("Books with title {0} was found. Author = {1}. Genre = {2}",
                book.getTitle(), book.getAuthor().getFullName(), book.getGenre().getName()));
    }

    @ShellMethod(key = "del", value = "delete book")
    public void deleteBook(@ShellOption String title) {
        bookService.deleteBook(title);

        System.out.println("Book was deleted");
    }

    @ShellMethod(key = "change-title", value = "change book title")
    public void changeTitle(@ShellOption String title, @ShellOption String newTitle) {
        bookService.changeTitle(title, newTitle);

        System.out.println("Book's title was changed");
    }

    @ShellMethod(key = "change-author", value = "change book author")
    public void changeAuthor(@ShellOption String title, @ShellOption String authorFullName) {
        bookService.changeAuthor(title, authorFullName);

        System.out.println("Book's author was changed");
    }

    @ShellMethod(key = "change-genre", value = "change book genre")
    public void changeGenre(@ShellOption String title, @ShellOption String genreName) {
        bookService.changeGenre(title, genreName);

        System.out.println("Book's genre was changed");
    }
}
