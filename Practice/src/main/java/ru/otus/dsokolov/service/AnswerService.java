package ru.otus.dsokolov.service;

import ru.otus.dsokolov.domain.Answer;

public interface AnswerService {

    Answer getCorrectByQuestionId(Long questionId);
}
