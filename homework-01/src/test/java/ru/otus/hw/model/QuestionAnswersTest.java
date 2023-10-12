package ru.otus.hw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс QuestionAnswers")
public class QuestionAnswersTest {

    @DisplayName("Проверка создания объекта")
    @Test

    void correctCreateObjectTest() {

        String testQuestion = "What is the name of our galaxy?";
        List<String> testAnswer = new ArrayList<>(List.of("1 Milky Way","2 Nabu","3 Magellanic cloud"));
        int testRightAnswerNumber = 1;

        QuestionAnswers questionAnswers = new QuestionAnswers(testQuestion,testAnswer,testRightAnswerNumber);

        assertEquals(testQuestion, questionAnswers.getQuestion());
        assertEquals(testAnswer, questionAnswers.getAnswers());
        assertEquals(3, questionAnswers.getAnswers().size());
        assertEquals(testRightAnswerNumber, questionAnswers.getRightAnswerNumber());
    }
}
