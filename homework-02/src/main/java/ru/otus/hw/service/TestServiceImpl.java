package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.model.Question;
import ru.otus.hw.model.Student;
import ru.otus.hw.model.TestResult;

import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below:%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            int numberOfAnswer = askQuestionGetAnswer(question); // Задать вопрос, получить ответ
            testResult.applyAnswer(question, question.answers().get(numberOfAnswer - 1).isCorrect());
        }
        return testResult;
    }

    private int askQuestionGetAnswer(Question question) {
        ioService.printLine(question.text());
        IntStream.range(0, question.answers().size())
                .forEach(i -> ioService.printLine((i + 1) + ". " + question.answers().get(i).text()));
        return ioService.readIntForRangeWithPrompt(1, question.answers().size(),
                "Enter the number of the correct answer: ", "Invalid input.");
    }
}
