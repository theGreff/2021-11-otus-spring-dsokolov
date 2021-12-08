package ru.otus.dsokolov.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class LocalizationConfig {
    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasename("classpath:/i18n/bundle");
        msgSrc.setDefaultEncoding("UTF-8");

        return msgSrc;
    }
}
