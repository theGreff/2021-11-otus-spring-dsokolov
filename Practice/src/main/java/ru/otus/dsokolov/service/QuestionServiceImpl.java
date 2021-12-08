package ru.otus.dsokolov.service;

import org.springframework.stereotype.Component;
import ru.otus.dsokolov.dao.QuestionDAO;
import ru.otus.dsokolov.domain.Question;

import java.util.List;

@Component
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDAO dao;

    public QuestionServiceImpl(final QuestionDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Question> loadAndGetQuestions() {
        dao.load();

        return dao.getAll();
    }
}
