package ru.otus.dsokolov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import ru.otus.dsokolov.dao.QuestionDAO;
import ru.otus.dsokolov.domain.Question;

import java.util.List;

@Component
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDAO dao;
    private List<Question> questionList;

    public QuestionServiceImpl(final QuestionDAO dao) {
        this.dao = dao;
    }

    @Override
    public void printQuestions() {
        if (questionList.isEmpty()) {
            throw new RuntimeException("Questions are not loaded");
        }

        System.out.println("Please, answer the questions below:");
        questionList.forEach(o -> {
            StringBuilder stringB = new StringBuilder();
            o.getAnswer().forEach(o1 -> stringB.append(o1.getSubj().trim()).append(" / "));
            System.out.println(o.getSubj() + " = (" + stringB.substring(0, stringB.length() - 3) + ")");
        });
    }

    @Override
    public List<Question> loadAndGetQuestions() {
        dao.load();

        return questionList = dao.getAll();
    }
}
