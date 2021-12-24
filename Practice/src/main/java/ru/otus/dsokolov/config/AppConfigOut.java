package ru.otus.dsokolov.config;

import org.springframework.stereotype.Component;

@Component
public class AppConfigOut {

    private final AppConfig appConfig;
    private final QuestionConfig questionConfig;
    private final LocalizationConfig localizationConfig;

    public AppConfigOut(final AppConfig appConfig, final QuestionConfig questionConfig, final LocalizationConfig localizationConfig) {
        this.appConfig = appConfig;
        this.questionConfig = questionConfig;
        this.localizationConfig = localizationConfig;
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public QuestionConfig getQuestionConfig() {
        return questionConfig;
    }

    public LocalizationConfig getLocalizationConfig() {
        return localizationConfig;
    }
}
