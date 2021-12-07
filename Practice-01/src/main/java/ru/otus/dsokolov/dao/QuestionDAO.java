package ru.otus.dsokolov.dao;

import ru.otus.dsokolov.domain.Answer;
import ru.otus.dsokolov.domain.Question;

import java.util.List;

public interface QuestionDAO {

    void load();

    List<Question> getAll();

    Answer getCorrectAnswerByQuestionId(Long questionId);
}
