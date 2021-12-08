package ru.otus.dsokolov.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.dsokolov.config.LocalizationConfig;
import ru.otus.dsokolov.config.QuestionConfig;
import ru.otus.dsokolov.domain.Answer;
import ru.otus.dsokolov.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("Loading data into Question bean")
class QuestionCSVTest {
    @Autowired
    QuestionConfig questionConfig;

    @Test
    void load() {
        QuestionDAO questionDAO = new QuestionCSV(questionConfig);
        questionDAO.load();
        List<Question> questionActualList = questionDAO.getAll();

        // 1+2; 3; 4;
        List<Question> questionExpectedList = new ArrayList<>();
        List<Answer> answerExpectedList = new ArrayList<>();
        answerExpectedList.add(new Answer(1, "1", true));
        answerExpectedList.add(new Answer(2, "2", false));
        questionExpectedList.add(new Question(1, "1+1", answerExpectedList));

        assertEquals(questionExpectedList.get(0).getSubj(), questionActualList.get(0).getSubj());
        assertEquals(questionExpectedList.get(0).getAnswer().get(0).getSubj(), questionActualList.get(0).getAnswer().get(0).getSubj());
        assertEquals(questionExpectedList.get(0).getAnswer().get(1).getSubj(), questionActualList.get(0).getAnswer().get(1).getSubj());
    }
}