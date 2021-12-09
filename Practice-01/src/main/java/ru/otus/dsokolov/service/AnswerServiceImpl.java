package ru.otus.dsokolov.service;

import org.springframework.stereotype.Component;
import ru.otus.dsokolov.dao.QuestionDAO;
import ru.otus.dsokolov.domain.Answer;
import ru.otus.dsokolov.domain.Question;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnswerServiceImpl implements AnswerService {
    private final List<Question> questionList = new ArrayList<>();

    public AnswerServiceImpl(QuestionDAO questionDAO) {
        questionList.addAll(questionDAO.getAll());
    }

    @Override
    public Answer getCorrectByQuestionId(Long questionId) {
        Question question = questionList.stream().filter(o -> questionId.equals((long) o.getId())).findFirst().orElse(null);

        if (question == null) {
            throw new RuntimeException("Can not find the Question with id + questionId");
        }

        return question.getAnswer().stream().filter(Answer::isCorrect).findFirst().orElse(null);
    }
}
