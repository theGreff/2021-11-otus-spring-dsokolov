package ru.otus.dsokolov.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class LocalizationConfig {

    private final AppConfig appConfig;

    public LocalizationConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasename(appConfig.getLocalePath());
        msgSrc.setDefaultEncoding("UTF-8");

        return msgSrc;
    }
}
