package ru.otus.dsokolov.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import ru.otus.dsokolov.domain.Answer;
import ru.otus.dsokolov.domain.Question;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Loading data into Question bean")
class QuestionCSVTest {

    @Test
    void load() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionDAO questionDAO = context.getBean(QuestionDAO.class);
        questionDAO.load( new ClassPathResource("/questions.csv"));
        List<Question> questionActualList = questionDAO.getAll();

        // 1+2; 3; 4;
        List<Question> questionExpectedList = new ArrayList<>();
        List<Answer> answerExpectedList = new ArrayList<>();
        answerExpectedList.add(new Answer(1, "3"));
        answerExpectedList.add(new Answer(2, "4"));
        questionExpectedList.add(new Question(1, "1+2", answerExpectedList));

        assertEquals(questionExpectedList.get(0).getSubj(), questionActualList.get(0).getSubj());
        assertEquals(questionExpectedList.get(0).getAnswer().get(0).getSubj(), questionActualList.get(0).getAnswer().get(0).getSubj());
        assertEquals(questionExpectedList.get(0).getAnswer().get(1).getSubj(), questionActualList.get(0).getAnswer().get(1).getSubj());
    }
}