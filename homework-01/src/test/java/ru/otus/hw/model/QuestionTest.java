package ru.otus.hw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Question")
public class QuestionTest {
    @DisplayName("Проверка создания объекта")
    @Test

    void correctCreateObjectTest() {

        String testQuestion = "What is the name of our galaxy?";
        List<Answer> testAnswers = new ArrayList<>(List.of(new Answer("Milky Way",true),
                new Answer("Nabu",false),
                new Answer("Magellanic cloud",false)));
        Question question = new Question(testQuestion, testAnswers);

        assertEquals(testQuestion, question.text());
        assertEquals(testAnswers, question.answers());
        assertEquals(3, testAnswers.size());
    }
}
