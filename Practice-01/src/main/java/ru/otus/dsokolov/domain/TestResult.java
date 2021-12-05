package ru.otus.dsokolov.domain;

import java.util.List;
import java.util.Map;

public class TestResult {
    private Person person;
    private List<Question> questions;
    private Map<Long, Long> personAnswers;
    private Map<Long, String> personResults;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Map<Long, Long> getPersonAnswers() {
        return personAnswers;
    }

    public void setPersonAnswers(Map<Long, Long> personAnswers) {
        this.personAnswers = personAnswers;
    }

    public Map<Long, String> getPersonResults() {
        return personResults;
    }

    public void setPersonResults(Map<Long, String> personResults) {
        this.personResults = personResults;
    }
}
