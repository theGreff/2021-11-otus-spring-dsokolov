package ru.otus.dsokolov.dao;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.otus.dsokolov.base.ParseServiceCSV;
import ru.otus.dsokolov.config.QuestionConfig;
import ru.otus.dsokolov.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionCSV implements QuestionDAO {

    private final ParseServiceCSV parceService;
    private final String recoursePath;


    public QuestionCSV(final QuestionConfig questionConfig, final ParseServiceCSV parceService) {
        this.parceService = parceService;
        this.recoursePath = questionConfig.getRecoursePath();
    }

    @Override
    public List<Question> getAll() {
        try {
            return parceService.parseQuestions(readResource());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private List<String> readResource() throws IOException {
        List<String> result = new ArrayList<>();

        Resource resource = new ClassPathResource(recoursePath);
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
