package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.model.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DisplayName("Класс CsvQuestionDao")
public class CsvQuestionDaoTest {
    private static final String FILE_NAME_SUCCESS = "/questions_test.csv";
    private static final String FILE_NAME_ERROR = "/questions_error.csv";
    private TestFileNameProvider testFileNameProvider;
    private CsvQuestionDao csvQuestionDao;

    @BeforeEach
    public void initEach(){
        testFileNameProvider = Mockito.mock(TestFileNameProvider.class);
        csvQuestionDao = new CsvQuestionDao(testFileNameProvider);
    }

    @DisplayName("Проверка чтения файла с вопросами - возвращаются вопросы")
    @Test
    void QuestionSuccess() {
        when(testFileNameProvider.getTestFileName()).thenReturn(FILE_NAME_SUCCESS);
        List<Question> questionList = csvQuestionDao.findAll();
        assertEquals(2, questionList.size());
    }

    @DisplayName("Проверка чтения файла с вопросами - возвращается исключение QuestionReadException")
    @Test
    void QuestionError() {
        when(testFileNameProvider.getTestFileName()).thenReturn(FILE_NAME_ERROR);
        assertThrows(QuestionReadException.class, csvQuestionDao::findAll,"Получено исключение FileNotFindException");
    }
}
