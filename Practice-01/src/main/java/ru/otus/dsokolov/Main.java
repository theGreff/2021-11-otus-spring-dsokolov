package ru.otus.dsokolov;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.dsokolov.service.TestResultsProcessService;

@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        TestResultsProcessService testResultsProcessService = context.getBean(TestResultsProcessService.class);

        testResultsProcessService.runTest();
    }
}
