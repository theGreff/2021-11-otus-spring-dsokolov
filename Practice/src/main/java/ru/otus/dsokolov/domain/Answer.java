package ru.otus.dsokolov.domain;

public class Answer {

    private int id;
    private String subj;
    private boolean isCorrect;

    public Answer(int id, String subj, boolean isCorrect) {
        this.id = id;
        this.subj = subj;
        this.isCorrect = isCorrect;
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

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
