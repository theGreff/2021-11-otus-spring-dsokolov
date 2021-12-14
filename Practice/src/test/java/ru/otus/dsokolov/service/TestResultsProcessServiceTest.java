package ru.otus.dsokolov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.dsokolov.base.ParseServiceCSV;
import ru.otus.dsokolov.config.AppConfigOut;
import ru.otus.dsokolov.dao.QuestionCSV;
import ru.otus.dsokolov.dao.QuestionDAO;
import ru.otus.dsokolov.domain.Person;
import ru.otus.dsokolov.domain.TestResult;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("Test of process test results")
public class TestResultsProcessServiceTest {

    @Autowired
    AppConfigOut appConfigOut;

    @Autowired
    ParseServiceCSV parseServiceCSV;

    @Test
    void processTestResult() {
        TestResult testResult = new TestResult();
        testResult.setPerson(new Person("firstName", "lastName"));

        QuestionDAO questionDAO = new QuestionCSV(appConfigOut.getQuestionConfig(), parseServiceCSV);
        QuestionServiceImpl questionService = new QuestionServiceImpl(appConfigOut.getAppConfig(),
                appConfigOut.getLocalizationConfig(), questionDAO);
        AnswerServiceImpl answerService = new AnswerServiceImpl(questionDAO);
        testResult.setQuestions(questionService.prepareForTest());

        TestServiceImpl testResultsProcessService = new TestServiceImpl(appConfigOut, answerService, questionService);
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
