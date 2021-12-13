package ru.otus.dsokolov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.dsokolov.service.TestServiceImpl;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Main.class, args);
        TestServiceImpl testResultsProcessService = ctx.getBean(TestServiceImpl.class);

        testResultsProcessService.runTest();
    }
}
