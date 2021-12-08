package ru.otus.dsokolov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import ru.otus.dsokolov.config.AppConfig;
import ru.otus.dsokolov.config.LocalizationConfig;
import ru.otus.dsokolov.config.QuestionConfig;
import ru.otus.dsokolov.dao.QuestionCSV;
import ru.otus.dsokolov.dao.QuestionDAO;
import ru.otus.dsokolov.domain.Person;
import ru.otus.dsokolov.domain.TestResult;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("Test of process test results")
public class TestResultsProcessServiceTest {

    @Autowired
    QuestionConfig questionConfig;
    @Autowired
    AppConfig appConfig;
    @Autowired
    LocalizationConfig localizationConfig;

    @Test
    void processTestResult() {
        TestResult testResult = new TestResult();
        testResult.setPerson(new Person("firstName", "lastName"));

        QuestionDAO questionDAO = new QuestionCSV(questionConfig);
        QuestionServiceImpl questionService = new QuestionServiceImpl(questionDAO);

        testResult.setQuestions(questionService.loadAndGetQuestions());

        TestResultsProcessService testResultsProcessService = new TestResultsProcessService(questionDAO,
                questionConfig, appConfig, localizationConfig);
        Map<Long, String> personAnswers = new HashMap<>();
        //1+1; 1; 2; 3; 2

        // set wrong answer
        personAnswers.put(1L, "3");
        testResult.setPersonAnswers(personAnswers);
        testResultsProcessService.processPersonAnswers(testResult);
        assertEquals(testResult.getPersonResults().get(1L), Boolean.FALSE);

        // set correct answer
        personAnswers.clear();
        personAnswers.put(1L, "2");
        testResult.setPersonAnswers(personAnswers);
        testResultsProcessService.processPersonAnswers(testResult);
        assertEquals(testResult.getPersonResults().get(1L), Boolean.TRUE);
    }
}
