package ru.otus.dsokolov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import ru.otus.dsokolov.domain.Answer;
import ru.otus.dsokolov.domain.Person;
import ru.otus.dsokolov.domain.Question;
import ru.otus.dsokolov.domain.TestResult;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
@PropertySource("classpath:application.properties")
public class TestResultsProcessService {

    @Value("${cnt.test.done}")
    private Long cntAnswerForDone;

    private final AnswerService answerService;
    private final QuestionService questionService;

    public TestResultsProcessService(final AnswerService answerService, final QuestionService questionService) {
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
        validatePersonTest(testResult);

        List<Boolean> correctAnswerCnt = testResult.getPersonResults().values()
                .stream().filter(Boolean.TRUE::equals).collect(Collectors.toList());

        if (correctAnswerCnt.size() >= cntAnswerForDone) {
            System.out.println("The Test is passed!");

            return true;
        } else {
            System.out.println("The Test is not passed!");

            return false;
        }
    }

    public void processPersonAnswers(TestResult testResult) {
        validatePersonTest(testResult);

        testResult.getQuestions().forEach(o -> {
            String gotAnswer = testResult.getPersonAnswers().get((long) o.getId());
            testResult.getPersonResults().put((long) o.getId(), processAnswer(o, gotAnswer));
        });

        System.out.println("----------- Results of testing " + testResult.getPerson().toString() + " -----------");

        testResult.getPersonResults()
                .forEach((key, value) -> System.out.println("Question number = " + key + "; Answer = " + value));
    }

    private void validatePersonTest(TestResult testResult) {
        if (testResult.getPerson() == null || ObjectUtils.isEmpty(testResult.getPerson().toString().trim())) {
            throw new RuntimeException("Person are not loaded");
        }
        if (testResult.getQuestions() == null || testResult.getQuestions().isEmpty()) {
            throw new RuntimeException("Questions are not loaded");
        }
        if (testResult.getPersonAnswers() == null || testResult.getPersonAnswers().isEmpty()) {
            throw new RuntimeException("Answers are not loaded");
        }
    }

    private void authPerson(Scanner scanner, TestResult testResult) {
        System.out.println("Enter your fist name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name: ");
        String lastName = scanner.nextLine();

        testResult.setPerson(new Person(firstName, lastName));
    }

    private void loadPersonAnswers(Scanner scanner, TestResult testResult) {
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
