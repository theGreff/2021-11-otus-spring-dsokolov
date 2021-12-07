package ru.otus.dsokolov.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.otus.dsokolov.domain.Answer;
import ru.otus.dsokolov.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource("classpath:application.properties")
public class QuestionCSV implements QuestionDAO {

    @Value("${question.res}")
    private String resPath;

    private List<Question> questionList = new ArrayList<>();

    private List<Answer> getAnswers(String[] line) {
        List<Answer> res = new ArrayList<>();

        // последним всегда идет номер правильного ответа
        int correctAnswerNum = Integer.parseInt(line[line.length - 1].trim());
        for (int i = 1; i < line.length - 1; i++) {
            res.add(new Answer(i, line[i].trim(), correctAnswerNum == i));
        }

        return res;
    }

    @Override
    public void load() {
        Resource resource = new ClassPathResource(resPath);
        String lineStr;
        int questionId = 1;
        //  0 - сам вопрос. 1-3 варинаты ответов, 4 - правильный номер(!) ответа
        try (InputStream inputStream = resource.getInputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((lineStr = bufferedReader.readLine()) != null) {
                String[] items = lineStr.split(";");
                questionList.add(new Question(questionId++, items[0].trim(), getAnswers(items)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Question> getAll() {
        return questionList;
    }

    @Override
    public Answer getCorrectAnswerByQuestionId(Long questionId) {
        Question question = questionList.stream().filter(o -> questionId.equals((long) o.getId())).findFirst().orElse(null);

        if (question == null) {
            throw new RuntimeException("Can not find the Question with id + questionId");
        }

        return question.getAnswer().stream().filter(Answer::isCorrect).findFirst().orElse(null);
    }
}
