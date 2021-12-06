package ru.otus.dsokolov.domain;

import java.util.List;

public class Question {
    private int id;

    private String subj;

    private List<Answer> answer;

    public Question(int id, String subj, List<Answer> answer) {
        this.id = id;
        this.subj = subj;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubj() {
        return subj;
    }

    public void setSubj(String subj) {
        this.subj = subj;
    }

    public List<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Answer> answer) {
        this.answer = answer;
    }
}
