package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.model.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        // Получить вопросы из дао и вывести их с вариантами ответов
        try {
            List<Question> questionList = questionDao.findAll();
            printQuestions(questionList);
        } catch (QuestionReadException qex) {
            ioService.printLine("Error reading csv file.");
        } catch (Exception ex) {
            ioService.printLine("Received an unknown error.");
        }
    }

    private void printQuestions(List<Question> questionList) {
        for (Question question : questionList) {
            ioService.printLine(question.text());
            question.answers().forEach(answer -> ioService.printLine(answer.text()));
            ioService.printLine("");
        }
    }
}
