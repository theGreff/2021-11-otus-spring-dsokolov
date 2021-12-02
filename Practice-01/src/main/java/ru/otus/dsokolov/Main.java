package ru.otus.dsokolov;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.dsokolov.service.QuestionService;

/**
 * Hello world!
 *
 */
public class Main
{

    public static void main( String[] args )
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService questionService = context.getBean(QuestionService.class);
        questionService.printAllQuestions();
    }
}
