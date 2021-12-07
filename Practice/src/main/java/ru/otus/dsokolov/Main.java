package ru.otus.dsokolov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.dsokolov.domain.Person;
import ru.otus.dsokolov.domain.TestResult;
import ru.otus.dsokolov.service.QuestionService;
import ru.otus.dsokolov.service.QuestionServiceImpl;
import ru.otus.dsokolov.service.TestResultsProcessService;

import java.util.Scanner;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter your fist name: ");
            String firstName = scanner.nextLine();
            System.out.println("Enter last name: ");
            String lastName = scanner.nextLine();

            TestResult testResult = new TestResult();
            testResult.setPerson(new Person(firstName, lastName));

            QuestionService questionService = context.getBean(QuestionServiceImpl.class);
            testResult.setQuestions(questionService.loadAndGetQuestions());
            System.out.println("Please, answer the questions below:");

            TestResultsProcessService testResultsProcessService = context.getBean(TestResultsProcessService.class);
            testResultsProcessService.loadPersonAnswers(testResult);
            testResultsProcessService.processPersonAnswers(testResult);
            testResultsProcessService.isTestPassed(testResult);
        }
    }
}
