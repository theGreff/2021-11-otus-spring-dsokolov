package ru.otus.dsokolov.base;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.dsokolov.domain.Answer;
import ru.otus.dsokolov.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("Parse service")
public class ParseServiceCSVTest {
    @Autowired
    ParseServiceCSV parseServiceCSV;
    String source = "5+3; 1; 2; 8; 3";

    @Test
    @DisplayName("Parse answers")
    void parseAnswers() {
        //  0 - сам вопрос. 1-3 варианты ответов, 4 - правильный номер(!) ответа
        String[] answesArr = source.split(";");
        List<Answer> actualAnswerList = parseServiceCSV.parseAnswers(answesArr);
        List<Answer> answerListExpected = new ArrayList<>();
        answerListExpected.add(new Answer(3, "8", true));

        assertEquals(answerListExpected.get(0).getSubj(), actualAnswerList.get(2).getSubj());
        assertEquals(answerListExpected.get(0).isCorrect(), actualAnswerList.get(2).isCorrect());
    }

    @Test
    @DisplayName("Parse questions")
    void parseQuestions() {
        //  0 - сам вопрос. 1-3 варианты ответов, 4 - правильный номер(!) ответа
        List<String> stringList = new ArrayList<>();
        stringList.add(source);

        List<Question> actualQuestionList = parseServiceCSV.parseQuestions(stringList);
        List<Question> answerListExpected = new ArrayList<>();
        List<Answer> answerList = new ArrayList<>();
        answerList.add(new Answer(1, "1", false));
        answerList.add(new Answer(2, "2", false));
        answerList.add(new Answer(3, "8", true));
        answerListExpected.add(new Question(1, "5+3", answerList));

        assertEquals(answerListExpected.get(0).getSubj(), actualQuestionList.get(0).getSubj());
        assertEquals(answerListExpected.get(0).getAnswer().size(), actualQuestionList.get(0).getAnswer().size());
    }
}
