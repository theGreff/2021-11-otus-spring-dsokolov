package ru.otus.dsokolov.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.dsokolov.config.AppConfig;
import ru.otus.dsokolov.config.LocalizationConfig;
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
    private final AppConfig appConfig;
    private final MessageSource messageSource;

    public TestResultsProcessService(final QuestionDAO questionDAO, final QuestionConfig questionConfig,
                                     final AppConfig appConfig, final LocalizationConfig localizationConfig) {
        this.questionDAO = questionDAO;
        this.correctAnswersToPassTest = questionConfig.getCorrectAnswersToPassTest();
        this.appConfig = appConfig;
        this.messageSource = localizationConfig.messageSource();
    }

    public void loadPersonAnswers(TestResult testResult) {
        if (testResult.getQuestions() == null || testResult.getQuestions().isEmpty()) {
            throw new RuntimeException(messageSource.getMessage("error.no-question", null,
                    appConfig.getLocale()));
        }

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
        if (testResult.getQuestions() == null || testResult.getQuestions().isEmpty()) {
            throw new RuntimeException(messageSource.getMessage("error.no-question", null,
                    appConfig.getLocale()));
        }

        testResult.getQuestions().forEach(o -> {
            String gotAnswer = testResult.getPersonAnswers().get((long) o.getId());
            testResult.getPersonResults().put((long) o.getId(), processAnswer(o, gotAnswer));
        });
        System.out.println(messageSource.getMessage("message.results-of-testing",
                new String[]{testResult.getPerson().toString()},
                appConfig.getLocale()));
        testResult.getPersonResults()
                .forEach((key, value) -> {
                    String answerTrueStr = messageSource.getMessage("message.answer-true",
                            new String[]{testResult.getPerson().toString()},
                            appConfig.getLocale());
                    String answerFalseStr = messageSource.getMessage("message.answer-false",
                            new String[]{testResult.getPerson().toString()},
                            appConfig.getLocale());

                    System.out.println(
                            messageSource.getMessage("message.question-number", new String[]{key.toString()},
                                    appConfig.getLocale()) + "; " +
                                    messageSource.getMessage("message.answer",
                                            new String[]{value ? answerTrueStr : answerFalseStr},
                                            appConfig.getLocale()));

                });
    }

    private Boolean processAnswer(Question question, String answer) {
        Answer answerDict = questionDAO.getCorrectAnswerByQuestionId((long) question.getId());

        return answerDict.getSubj().equals(answer);
    }

    public boolean isTestPassed(TestResult testResult) {
        List<Boolean> correctAnswerCnt = testResult.getPersonResults().values()
                .stream().filter(Boolean.TRUE::equals).collect(Collectors.toList());

        if (correctAnswerCnt.size() >= correctAnswersToPassTest) {
            System.out.println(messageSource.getMessage("message.test-passed",
                    new String[]{testResult.getPerson().toString()},
                    appConfig.getLocale()));

            return true;
        } else {
            System.out.println(messageSource.getMessage("message.test-not-passed",
                    new String[]{testResult.getPerson().toString()},
                    appConfig.getLocale()));

            return false;
        }
    }
}
