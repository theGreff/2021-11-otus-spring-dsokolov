package ru.otus.dsokolov.base;

import org.springframework.stereotype.Component;
import ru.otus.dsokolov.domain.Answer;
import ru.otus.dsokolov.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ParseServiceCSV implements ParseService {

    @Override
    public List<Answer> parseAnswers(String[] line) {
        List<Answer> res = new ArrayList<>();

        // последним всегда идет номер правильного ответа
        int correctAnswerNum = Integer.parseInt(line[line.length - 1].trim());
        for (int i = 1; i < line.length - 1; i++) {
            res.add(new Answer(i, line[i].trim(), correctAnswerNum == i));
        }

        return res;
    }

    @Override
    public List<Question> parseQuestions(List<String> data) {
        List<Question> res = new ArrayList<>();

        AtomicInteger questionId = new AtomicInteger(1);
        data.forEach(o -> {
            //  0 - сам вопрос. 1-3 варианты ответов, 4 - правильный номер(!) ответа
            String[] items = o.split(";");
            res.add(new Question(questionId.getAndIncrement(), items[0].trim(), parseAnswers(items)));
        });

        return res;
    }
}
