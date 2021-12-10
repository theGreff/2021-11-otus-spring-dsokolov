package ru.otus.dsokolov.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import ru.otus.dsokolov.config.AppConfig;
import ru.otus.dsokolov.config.LocalizationConfig;
import ru.otus.dsokolov.config.QuestionConfig;
import ru.otus.dsokolov.domain.Answer;
import ru.otus.dsokolov.domain.Person;
import ru.otus.dsokolov.domain.Question;
import ru.otus.dsokolov.domain.TestResult;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class TestResultsProcessService {

    private final int correctAnswersToPassTest;
    private final AppConfig appConfig;
    private final MessageSource messageSource;
    private final AnswerService answerService;
    private final QuestionService questionService;

    public TestResultsProcessService(final AppConfig appConfig, final QuestionConfig questionConfig,
                                     final LocalizationConfig localizationConfig,
                                     final AnswerService answerService, final QuestionService questionService) {
        this.appConfig = appConfig;
        this.correctAnswersToPassTest = questionConfig.getCorrectAnswersToPassTest();
        this.messageSource = localizationConfig.messageSource();
        this.answerService = answerService;
        this.questionService = questionService;
    }

    public boolean runTest() {
        try (Scanner scanner = new Scanner(System.in)) {
            TestResult testResult = new TestResult();

            authPerson(scanner, testResult);
            testResult.setQuestions(questionService.prepareForTest());
            loadPersonAnswers(scanner, testResult);
            processPersonAnswers(testResult);

            return isTestPassed(testResult);
        }
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

    public void processPersonAnswers(TestResult testResult) {
        validatePersonTest(testResult);

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

    private void validatePersonTest(TestResult testResult) {
        checkPersonLoaded(testResult);
        checkQuestionsLoaded(testResult);
        checkPersonAnswersLoaded(testResult);
    }

    private void checkPersonLoaded(TestResult testResult) {
        if (testResult.getPerson() == null || ObjectUtils.isEmpty(testResult.getPerson().toString().trim())) {
            throw new RuntimeException(messageSource.getMessage("error.no-person", null,
                    appConfig.getLocale()));
        }
    }

    private void checkQuestionsLoaded(TestResult testResult) {
        if (testResult.getPersonAnswers() == null || testResult.getPersonAnswers().isEmpty()) {
            throw new RuntimeException(messageSource.getMessage("error.no-questions", null,
                    appConfig.getLocale()));
        }
    }

    private void checkPersonAnswersLoaded(TestResult testResult) {
        if (testResult.getQuestions() == null || testResult.getQuestions().isEmpty()) {
            throw new RuntimeException(messageSource.getMessage("error.no-person-answers", null,
                    appConfig.getLocale()));
        }
    }

    private void authPerson(Scanner scanner, TestResult testResult) {
        System.out.println(messageSource.getMessage("message.enter-first-name", null, appConfig.getLocale()));
        String firstName = scanner.nextLine();
        System.out.println(messageSource.getMessage("message.enter-last-name", null, appConfig.getLocale()));
        String lastName = scanner.nextLine();

        testResult.setPerson(new Person(firstName, lastName));
    }

    private void loadPersonAnswers(Scanner scanner, TestResult testResult) {
        checkQuestionsLoaded(testResult);

        testResult.getQuestions().forEach(o -> {
            StringBuilder stringB = new StringBuilder();
            o.getAnswer().forEach(o1 -> stringB.append(o1.getSubj().trim()).append(" / "));

            System.out.println(o.getSubj() + " = (" + stringB.substring(0, stringB.length() - 3) + ")");

            String gotAnswer = scanner.nextLine();
            testResult.getPersonAnswers().put((long) o.getId(), gotAnswer.trim());
        });
    }

    private Boolean processAnswer(Question question, String answer) {
        Answer answerDict = answerService.getCorrectByQuestionId((long) question.getId());

        return answerDict.getSubj().equals(answer);
    }
}
