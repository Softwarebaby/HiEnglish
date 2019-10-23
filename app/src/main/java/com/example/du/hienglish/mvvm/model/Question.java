package com.example.du.hienglish.mvvm.model;

import java.util.List;

public class Question {
    private String question;
    private List<String> answers;
    private int rightIndex;

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setRightIndex(int rightIndex) {
        this.rightIndex = rightIndex;
    }

    public int getRightIndex() {
        return rightIndex;
    }
}
