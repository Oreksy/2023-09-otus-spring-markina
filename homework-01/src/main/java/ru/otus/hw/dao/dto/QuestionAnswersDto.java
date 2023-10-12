package ru.otus.hw.dao.dto;

import com.opencsv.bean.CsvBindAndSplitByPosition;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import ru.otus.hw.model.QuestionAnswers;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionAnswersDto {

    @CsvBindByPosition(position = 0)
    private String text;

    @CsvBindAndSplitByPosition(position = 1, collectionType = ArrayList.class, elementType = String.class,
            splitOn = "\\|")
    private List<String> answers;

    @CsvBindByPosition(position = 2)
    private int rightAnswerNumber;

    public QuestionAnswers toModelObject() {
        return new QuestionAnswers(text, answers, rightAnswerNumber);
    }
}
