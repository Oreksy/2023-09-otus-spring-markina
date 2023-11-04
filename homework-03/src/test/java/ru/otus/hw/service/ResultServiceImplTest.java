package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.config.TestConfig;
import ru.otus.hw.model.Answer;
import ru.otus.hw.model.Question;
import ru.otus.hw.model.Student;
import ru.otus.hw.model.TestResult;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.ResultServiceImpl;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Класс ResultServiceImpl")
public class ResultServiceImplTest {
    int countAnswerSuccess = 2;
    int countAnswerNotSuccess = 3;

    private TestConfig testConfig;
    private LocalizedIOService ioService;

    private TestResult testResult;

    @BeforeEach
    public void initEach(){
        testConfig = Mockito.mock(TestConfig.class);
        ioService = Mockito.mock(LocalizedIOService.class);

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
        when(testConfig.getRightAnswersCountToPass()).thenReturn(countAnswerSuccess);
        ResultServiceImpl resultService = new ResultServiceImpl(testConfig, ioService);
        resultService.showResult(testResult);
        verify(ioService).printLineLocalized("ResultService.passed.test");
    }

    @DisplayName("Тестирование неуспешно")
    @Test
    void testNotSuccess() {
        when(testConfig.getRightAnswersCountToPass()).thenReturn(countAnswerNotSuccess);
        ResultServiceImpl resultService = new ResultServiceImpl(testConfig, ioService);
        resultService.showResult(testResult);
        verify(ioService).printLineLocalized("ResultService.fail.test");
    }
}
