package ru.otus.dsokolov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import ru.otus.dsokolov.config.AppConfig;
import ru.otus.dsokolov.config.LocalizationConfig;
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
            AppConfig appConfig = context.getBean(AppConfig.class);
            LocalizationConfig localizationConfig = context.getBean(LocalizationConfig.class);
            MessageSource messageSource = localizationConfig.messageSource();

            System.out.println(messageSource.getMessage("message.enter-first-name", null, appConfig.getLocale()));
            String firstName = scanner.nextLine();
            System.out.println(messageSource.getMessage("message.enter-last-name", null, appConfig.getLocale()));
            String lastName = scanner.nextLine();

            TestResult testResult = new TestResult();
            testResult.setPerson(new Person(firstName, lastName));

            QuestionService questionService = context.getBean(QuestionServiceImpl.class);
            testResult.setQuestions(questionService.loadAndGetQuestions());

            System.out.println(messageSource.getMessage("message.enter-answer-questions", null, appConfig.getLocale()));
            TestResultsProcessService testResultsProcessService = context.getBean(TestResultsProcessService.class);
            testResultsProcessService.loadPersonAnswers(testResult);
            testResultsProcessService.processPersonAnswers(testResult);
            testResultsProcessService.isTestPassed(testResult);
        }
    }
}
