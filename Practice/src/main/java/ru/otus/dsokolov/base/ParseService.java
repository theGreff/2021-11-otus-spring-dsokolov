package ru.otus.dsokolov.base;

import ru.otus.dsokolov.domain.Answer;
import ru.otus.dsokolov.domain.Question;

import java.util.List;

interface ParseService {

    List<Answer> parseAnswers(String[] line);

    List<Question> parseQuestions(List<String> data);
}
