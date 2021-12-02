package ru.otus.dsokolov.dao;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.dsokolov.domain.Answer;
import ru.otus.dsokolov.domain.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionCSV implements QuestionDAO {

    private List<Question> questionList = new ArrayList<>();

    private List<Answer> getAnswers(String[] line) {
        List<Answer> res = new ArrayList<>();
        for (int i = 1; i < line.length; i++) {
            res.add(new Answer(i, line[i].trim()));
        }
        return res;
    }

    @Override
    public void load(Resource resource) {
        String lineStr;
        int questionId = 1;
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
}
