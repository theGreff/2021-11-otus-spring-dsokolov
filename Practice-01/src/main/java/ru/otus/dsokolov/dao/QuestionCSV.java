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
import java.util.concurrent.atomic.AtomicInteger;

@Component
@PropertySource("classpath:application.properties")
public class QuestionCSV implements QuestionDAO {

    @Value("${question.res}")
    private String resPath;

    private final List<Question> questionList = new ArrayList<>();

    @Override
    public List<Question> getAll() {
        try {
            load(readResource());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return questionList;
    }

    private List<Answer> parseAnswers(String[] line) {
        List<Answer> res = new ArrayList<>();

        // последним всегда идет номер правильного ответа
        int correctAnswerNum = Integer.parseInt(line[line.length - 1].trim());
        for (int i = 1; i < line.length - 1; i++) {
            res.add(new Answer(i, line[i].trim(), correctAnswerNum == i));
        }

        return res;
    }

    private void load(List<String> data) {
        questionList.clear();

        AtomicInteger questionId = new AtomicInteger(1);
        data.forEach(o -> {
            //  0 - сам вопрос. 1-3 варианты ответов, 4 - правильный номер(!) ответа
            String[] items = o.split(";");
            questionList.add(new Question(questionId.getAndIncrement(), items[0].trim(), parseAnswers(items)));
        });
    }

    private List<String> readResource() throws IOException {
        List<String> result = new ArrayList<>();

        Resource resource = new ClassPathResource(resPath);
        String lineStr;
        //  0 - сам вопрос. 1-3 варианты ответов, 4 - правильный номер(!) ответа
        try (InputStream inputStream = resource.getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((lineStr = bufferedReader.readLine()) != null) {
                result.add(lineStr);
            }
        }

        return result;
    }
}
