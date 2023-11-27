package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.model.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
@SpringBootTest(classes = CsvQuestionDao.class)
@DisplayName("Класс CsvQuestionDao")

public class CsvQuestionDaoTest {
    private static final String FILE_NAME_SUCCESS = "/questions_test.csv";
    private static final String FILE_NAME_SUCCESS_RU = "/questions_test_ru.csv";
    private static final String FILE_NAME_ERROR = "/questions_error.csv";

    @MockBean
    private TestFileNameProvider testFileNameProvider;

    @Autowired
    private CsvQuestionDao csvQuestionDao;

    @DisplayName("Проверка чтения файла с вопросами - возвращаются вопросы")
    @Test
    void questionSuccess() {
        when(testFileNameProvider.getTestFileName()).thenReturn(FILE_NAME_SUCCESS);
        List<Question> questionList = csvQuestionDao.findAll();
        assertEquals(2, questionList.size());
    }

    @DisplayName("Проверка чтения файла с вопросами - возвращаются вопросы")
    @Test
    void questionSuccessRu() {
        when(testFileNameProvider.getTestFileName()).thenReturn(FILE_NAME_SUCCESS_RU);
        List<Question> questionList = csvQuestionDao.findAll();
        assertEquals(3, questionList.size());
    }

    @DisplayName("Проверка чтения файла с вопросами - возвращается исключение QuestionReadException")
    @Test
    void questionError() {
        when(testFileNameProvider.getTestFileName()).thenReturn(FILE_NAME_ERROR);
        assertThrows(QuestionReadException.class, csvQuestionDao::findAll,"Получено исключение QuestionReadException");
    }
}