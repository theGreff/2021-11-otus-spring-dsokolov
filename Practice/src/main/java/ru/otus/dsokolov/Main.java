package ru.otus.dsokolov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.dsokolov.service.TestResultsProcessService;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Main.class, args);
        TestResultsProcessService testResultsProcessService = ctx.getBean(TestResultsProcessService.class);

        testResultsProcessService.runTest();
    }
}
