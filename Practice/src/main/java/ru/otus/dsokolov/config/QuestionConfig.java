package ru.otus.dsokolov.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "question")
@Component
public class QuestionConfig {

    private String recoursePath;
    private int correctAnswersToPassTest;

    public String getRecoursePath() {
        return recoursePath;
    }

    public void setRecoursePath(String recoursePath) {
        this.recoursePath = recoursePath;
    }

    public int getCorrectAnswersToPassTest() {
        return correctAnswersToPassTest;
    }

    public void setCorrectAnswersToPassTest(int correctAnswersToPassTest) {
        this.correctAnswersToPassTest = correctAnswersToPassTest;
    }
}
