package ru.otus.dsokolov.domain;

public class Answer {
    private int id;
    private String subj;
    private boolean isCorrect;

    public int getId() {
        return id;
    }

    public Answer(int id, String subj) {
        this.id = id;
        this.subj = subj;
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
}
