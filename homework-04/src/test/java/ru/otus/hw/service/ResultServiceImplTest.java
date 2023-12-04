package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.config.TestConfig;
import ru.otus.hw.model.Answer;
import ru.otus.hw.model.Question;
import ru.otus.hw.model.Student;
import ru.otus.hw.model.TestResult;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ResultServiceImpl.class)
@DisplayName("Класс ResultServiceImpl")
public class ResultServiceImplTest {

    private TestResult testResult;
    private static final int COUNT_ANSWER_SUCCESS = 2;
    private static final int COUNT_ANSWER_NOT_SUCCESS = 3;

    @MockBean
    private TestConfig testConfig;

    @MockBean
    private LocalizedIOService ioService;

    @Autowired
    private ResultServiceImpl resultService;

    @BeforeEach
    public void initEach(){
        Student student = new Student("LastName", "FirstName");
        Answer answer11 = new Answer("Answer1",true);
        Answer answer12 = new Answer("Answer2",false);
        Question question1 = new Question("Question1", List.of(answer11,answer12));
        Question question2 = new Question("Question2", List.of(answer11,answer12));
        Question question3 = new Question("Question3", List.of(answer11,answer12));
        testResult = new TestResult(student);
        testResult.applyAnswer(question1,true);
        testResult.applyAnswer(question2,true);
        testResult.applyAnswer(question3,false);
    }

    @DisplayName("Тестирование успешно")
    @Test
    void testSuccess() {
        when(testConfig.getRightAnswersCountToPass()).thenReturn(COUNT_ANSWER_SUCCESS);
        resultService.showResult(testResult);
        verify(ioService).printLineLocalized("ResultService.passed.test");
    }

    @DisplayName("Тестирование неуспешно")
    @Test
    void testNotSuccess() {
        when(testConfig.getRightAnswersCountToPass()).thenReturn(COUNT_ANSWER_NOT_SUCCESS);
        resultService.showResult(testResult);
        verify(ioService).printLineLocalized("ResultService.fail.test");
    }
}
