package ru.otus.dsokolov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.otus.dsokolov.dao.QuestionDAO;
import ru.otus.dsokolov.domain.Answer;
import ru.otus.dsokolov.domain.Question;
import ru.otus.dsokolov.domain.TestResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
@PropertySource("classpath:app.properties")
public class TestResultsProcessService {
    private final QuestionDAO questionDAO;

    @Value("${cnt.test.done}")
    private Long cntAnswerForDone;

    private final Map<Long, Boolean> testResults = new HashMap<>();

    public TestResultsProcessService(final QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    public Map<Long, Boolean> processTest(TestResult testResult) {
        Scanner scanner = new Scanner(System.in);

        testResult.getQuestions().forEach(o -> {
            StringBuilder stringB = new StringBuilder();
            o.getAnswer().forEach(o1 -> stringB.append(o1.getSubj().trim()).append(" / "));
            System.out.println(o.getSubj() + " = (" + stringB.substring(0, stringB.length() - 3) + ")");
            String gotAnswer = scanner.nextLine();

            testResults.put((long) o.getId(), processAnswer(o, gotAnswer));
        });

        System.out.println("----------- Results of testing " + testResult.getPerson().toString() + " -----------");
        testResults.forEach((key, value) -> System.out.println("Question number = " + key + "; Answer = " + value));

        return testResults;
    }

    private Boolean processAnswer(Question question, String answer) {
        Answer answerDict = questionDAO.getCorrectAnswerByQuestionId((long) question.getId());

        return answerDict.getSubj().equals(answer);
    }

    public boolean isTestPassed() {
        List<Boolean> correctAnswerCnt = testResults.values().stream().filter(Boolean.TRUE::equals).collect(Collectors.toList());

        if (correctAnswerCnt.size() >= cntAnswerForDone) {
            System.out.println("The Test is passed!");

            return true;
        } else {
            System.out.println("The Test is not passed!");

            return false;
        }
    }
}
