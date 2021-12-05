package ru.otus.dsokolov.dao;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.otus.dsokolov.domain.Answer;
import ru.otus.dsokolov.domain.Question;

import java.util.List;

@Component
public interface QuestionDAO {
    void load();

    List<Question> getAll();

    Answer getCorrectAnswerByQuestionId(Long questionId);
}
