package ru.otus.dsokolov;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.dsokolov.dao.BookDao;
import ru.otus.dsokolov.domain.Book;

import java.sql.SQLException;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws SQLException {
        ApplicationContext ctx = SpringApplication.run(Main.class, args);

        BookDao bookDao = ctx.getBean(BookDao.class);
        Book book = new Book();
        book.setTitle("AAAAAAAAAAAAA");
        bookDao.insert(book);
        System.out.println("Count = " + bookDao.count());

        Console.main(args);
    }
}
