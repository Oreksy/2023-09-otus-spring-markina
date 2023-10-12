package ru.otus.hw.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionAnswers {
    private String question;

    private List<String> answers;

    private int rightAnswerNumber;

    public QuestionAnswers(String question, List<String> answers, int trueNumberAnswer) {
        this.question = question;
        this.answers = answers;
        this.rightAnswerNumber = trueNumberAnswer;
    }
}
