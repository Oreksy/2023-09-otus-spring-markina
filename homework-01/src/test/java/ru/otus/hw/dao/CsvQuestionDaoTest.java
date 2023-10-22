package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.exceptions.FileNotFindException;
import ru.otus.hw.model.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DisplayName("Класс CsvQuestionDao")
public class CsvQuestionDaoTest {
    private final String FILE_NAME_SUCCESS = "/questions_test.csv";
    private final String FILE_NAME_ERROR = "/questions_error.csv";

    @DisplayName("Проверка чтения файла с вопросами - возвращаются вопросы")
    @Test
    void QuestionSuccess() {

        TestFileNameProvider testFileNameProvider = Mockito.mock(TestFileNameProvider.class);
        when(testFileNameProvider.getTestFileName()).thenReturn(FILE_NAME_SUCCESS);
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(testFileNameProvider);
        List<Question> questionList = csvQuestionDao.findAll();
        assertEquals(2, questionList.size());
    }

    @DisplayName("Проверка чтения файла с вопросами - возвращаетс яисключение FileNotFindException")
    @Test
    void QuestionError() {

        TestFileNameProvider testFileNameProvider = Mockito.mock(TestFileNameProvider.class);
        when(testFileNameProvider.getTestFileName()).thenReturn(FILE_NAME_ERROR);
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(testFileNameProvider);
        assertThrows(FileNotFindException.class, csvQuestionDao::findAll,"Получено исключение FileNotFindException");
    }
}
