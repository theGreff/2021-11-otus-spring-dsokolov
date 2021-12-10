package ru.otus.dsokolov.dao;

import ru.otus.dsokolov.domain.Question;

import java.util.List;

public interface QuestionDAO {

    List<Question> getAll();
}
