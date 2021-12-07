package ru.otus.dsokolov.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestResult {

    private Person person;
    private List<Question> questions;
    private Map<Long, String> personAnswers = new HashMap<>();
    private Map<Long, Boolean> personResults = new HashMap<>();

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

    public Map<Long, String> getPersonAnswers() {
        return personAnswers;
    }

    public void setPersonAnswers(Map<Long, String> personAnswers) {
        this.personAnswers = personAnswers;
    }

    public Map<Long, Boolean> getPersonResults() {
        return personResults;
    }

    public void setPersonResults(Map<Long, Boolean> personResults) {
        this.personResults = personResults;
    }
}
