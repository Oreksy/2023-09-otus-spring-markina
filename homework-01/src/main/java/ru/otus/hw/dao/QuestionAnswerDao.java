package ru.otus.hw.dao;

import ru.otus.hw.model.QuestionAnswers;

import java.util.List;

public interface QuestionAnswerDao {
    List<QuestionAnswers> findAll();
}
