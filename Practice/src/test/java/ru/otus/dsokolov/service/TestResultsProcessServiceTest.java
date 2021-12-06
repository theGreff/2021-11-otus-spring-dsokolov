package ru.otus.dsokolov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.dsokolov.Main;
import ru.otus.dsokolov.domain.Person;
import ru.otus.dsokolov.domain.TestResult;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test of process test results")
public class TestResultsProcessServiceTest {
    @Test
    void processTestResult() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        TestResult testResult = new TestResult();
        testResult.setPerson(new Person("firstName", "lastName"));
        QuestionService questionService = context.getBean(QuestionServiceImpl.class);
        testResult.setQuestions(questionService.loadAndGetQuestions());

        TestResultsProcessService testResultsProcessService = context.getBean(TestResultsProcessService.class);
        Map<Long, String> personAnswers = new HashMap<>();
        //1+2; 3; 4; ; 1

        // set wrong answer
        personAnswers.put(1L, "4");
        testResult.setPersonAnswers(personAnswers);
        testResultsProcessService.processPersonAnswers(testResult);
        assertEquals(testResult.getPersonResults().get(1L), Boolean.FALSE);

        // set correct answer
        personAnswers.clear();
        personAnswers.put(1L, "3");
        testResult.setPersonAnswers(personAnswers);
        testResultsProcessService.processPersonAnswers(testResult);

        assertEquals(testResult.getPersonResults().get(1L), Boolean.TRUE);
    }
}
