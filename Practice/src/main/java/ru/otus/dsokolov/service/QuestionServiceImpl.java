package ru.otus.dsokolov.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.dsokolov.config.AppConfig;
import ru.otus.dsokolov.config.LocalizationConfig;
import ru.otus.dsokolov.dao.QuestionDAO;
import ru.otus.dsokolov.domain.Question;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionServiceImpl implements QuestionService {

    private final AppConfig appConfig;
    private final MessageSource messageSource;
    private final List<Question> questionList = new ArrayList<>();

    public QuestionServiceImpl(final AppConfig appConfig, final LocalizationConfig localizationConfig, final QuestionDAO dao) {
        this.appConfig = appConfig;
        this.messageSource = localizationConfig.messageSource();

        this.questionList.addAll(dao.getAll());
    }

    @Override
    public void printQuestions() {
        if (questionList.isEmpty()) {
            throw new RuntimeException("Questions are not loaded");
        }

        questionList.forEach(o -> {
            StringBuilder stringB = new StringBuilder();
            o.getAnswer().forEach(o1 -> stringB.append(o1.getSubj().trim()).append(" / "));
            System.out.println(o.getSubj() + " = (" + stringB.substring(0, stringB.length() - 3) + ")");
        });
    }

    @Override
    public List<Question> prepareForTest() {
        System.out.println(messageSource.getMessage("message.enter-answer-questions", null, appConfig.getLocale()));

        return questionList;
    }
}
