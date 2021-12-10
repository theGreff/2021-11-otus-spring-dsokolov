package ru.otus.dsokolov.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@ConfigurationProperties(prefix = "application")
public class AppConfig {

    Locale locale;
    String localePath;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getLocalePath() {
        return localePath;
    }

    public void setLocalePath(String localePath) {
        this.localePath = localePath;
    }
}
