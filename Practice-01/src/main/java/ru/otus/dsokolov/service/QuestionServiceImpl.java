package ru.otus.dsokolov.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.dsokolov.dao.QuestionDAO;
import ru.otus.dsokolov.domain.Question;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {
    private QuestionDAO dao;

    public QuestionServiceImpl(QuestionDAO dao) {
        this.dao = dao;
    }

    @Override
    public void printAllQuestions() {
        dao.load(new ClassPathResource("/questions.csv"));

        List<Question> questionList = dao.getAll();
        System.out.println("Please, answer the questions below:");
        questionList.forEach(o -> {
            StringBuilder stringB = new StringBuilder();
            o.getAnswer().forEach(o1 -> stringB.append(o1.getSubj().trim()).append(" / "));
            System.out.println(o.getSubj() + " = (" + stringB.substring(0, stringB.length()-3) + ")");

        });
    }
}
