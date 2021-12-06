package ru.otus.dsokolov.service;

import org.springframework.stereotype.Component;
import ru.otus.dsokolov.config.QuestionConfig;
import ru.otus.dsokolov.dao.QuestionDAO;
import ru.otus.dsokolov.domain.Answer;
import ru.otus.dsokolov.domain.Question;
import ru.otus.dsokolov.domain.TestResult;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class TestResultsProcessService {

    private final QuestionDAO questionDAO;
    private final int correctAnswersToPassTest;

    public TestResultsProcessService(final QuestionDAO questionDAO, final QuestionConfig questionConfig) {
        this.questionDAO = questionDAO;
        this.correctAnswersToPassTest = questionConfig.getCorrectAnswersToPassTest();
    }

    public void loadPersonAnswers(TestResult testResult) {
        try (Scanner scanner = new Scanner(System.in)) {
            testResult.getQuestions().forEach(o -> {
                StringBuilder stringB = new StringBuilder();
                o.getAnswer().forEach(o1 -> stringB.append(o1.getSubj().trim()).append(" / "));
                System.out.println(o.getSubj() + " = (" + stringB.substring(0, stringB.length() - 3) + ")");
                String gotAnswer = scanner.nextLine();

                testResult.getPersonAnswers().put((long) o.getId(), gotAnswer.trim());
            });
        }
    }

    public void processPersonAnswers(TestResult testResult) {
        if (testResult.getQuestions().isEmpty()) {
            throw new RuntimeException("Questions are not loaded");
        }

        testResult.getQuestions().forEach(o -> {
            String gotAnswer = testResult.getPersonAnswers().get((long) o.getId());
            testResult.getPersonResults().put((long) o.getId(), processAnswer(o, gotAnswer));
        });

        System.out.println("----------- Results of testing " + testResult.getPerson().toString() + " -----------");
        testResult.getPersonResults()
                .forEach((key, value) -> System.out.println("Question number = " + key + "; Answer = " + value));
    }

    private Boolean processAnswer(Question question, String answer) {
        Answer answerDict = questionDAO.getCorrectAnswerByQuestionId((long) question.getId());

        return answerDict.getSubj().equals(answer);
    }

    public boolean isTestPassed(TestResult testResult) {
        List<Boolean> correctAnswerCnt = testResult.getPersonResults().values()
                .stream().filter(Boolean.TRUE::equals).collect(Collectors.toList());

        if (correctAnswerCnt.size() >= correctAnswersToPassTest) {
            System.out.println("The Test is passed!");

            return true;
        } else {
            System.out.println("The Test is not passed!");

            return false;
        }
    }
}
