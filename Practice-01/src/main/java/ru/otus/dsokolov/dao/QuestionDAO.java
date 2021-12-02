package ru.otus.dsokolov.dao;

import org.springframework.core.io.Resource;
import ru.otus.dsokolov.domain.Question;

import java.util.List;

public interface QuestionDAO {
    public void load(Resource resource);

    List<Question> getAll();
}
