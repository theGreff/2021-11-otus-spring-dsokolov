package ru.otus.dsokolov;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.dsokolov.service.BookService;

import java.sql.SQLException;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws SQLException {
        ApplicationContext ctx = SpringApplication.run(Main.class, args);
        BookService bookService = ctx.getBean(BookService.class);

        Console.main(args);
    }
}
