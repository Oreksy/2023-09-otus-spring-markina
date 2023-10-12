package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionAnswerDao;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.model.QuestionAnswers;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionAnswerDao questionAnswerDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        // Получить вопросы из дао и вывести их с вариантами ответов
        try {
            List<QuestionAnswers> questionAnswersList = questionAnswerDao.findAll();
            for (QuestionAnswers questionAnswers : questionAnswersList) {
                ioService.printFormattedLine(questionAnswers.getQuestion());
                questionAnswers.getAnswers().forEach(answer -> ioService.printFormattedLine(answer));
            }
        } catch (QuestionReadException qex) {
            ioService.printLine(qex.getMessage());
        } catch (Exception ex) {
            ioService.printLine("Произошла неизвестная ошибка");
        }
    }
}
