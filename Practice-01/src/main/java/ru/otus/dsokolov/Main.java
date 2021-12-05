package ru.otus.dsokolov;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.dsokolov.domain.Person;
import ru.otus.dsokolov.domain.Question;
import ru.otus.dsokolov.domain.TestResult;
import ru.otus.dsokolov.service.QuestionService;
import ru.otus.dsokolov.service.QuestionServiceImpl;
import ru.otus.dsokolov.service.TestResultsProcessService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        Scanner scanner = new Scanner(System.in);
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
        testResultsProcessService.processTest(testResult);
        testResultsProcessService.isTestPassed();
    }
}
