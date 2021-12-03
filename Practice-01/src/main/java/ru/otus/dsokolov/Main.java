package ru.otus.dsokolov;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.dsokolov.service.QuestionService;

@ComponentScan
@Configuration
public class Main
{

    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        QuestionService questionService = context.getBean(QuestionService.class);
        questionService.printAllQuestions();
    }
}
