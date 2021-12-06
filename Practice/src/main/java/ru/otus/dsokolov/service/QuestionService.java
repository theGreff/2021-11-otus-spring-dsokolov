package ru.otus.dsokolov.service;

import ru.otus.dsokolov.domain.Question;

import java.util.List;

public interface QuestionService {
    List<Question> loadAndGetQuestions();

    void printQuestions();
}
