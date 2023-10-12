package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionAnswersDto;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.model.QuestionAnswers;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class QuestionAnswerDaoImpl implements  QuestionAnswerDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<QuestionAnswers> findAll() {

        try {
            InputStream inputStream = this.getClass().getResourceAsStream(fileNameProvider.getTestFileName());
            InputStreamReader reader = new InputStreamReader(inputStream);

            List<QuestionAnswersDto> questionAnswersDtoList = new CsvToBeanBuilder(reader)
                    .withType(QuestionAnswersDto.class)
                    .withSeparator(';')
                    .build()
                    .parse();

            List<QuestionAnswers> questionAnswersList = new ArrayList<>();
            questionAnswersDtoList.forEach(qaDto ->  questionAnswersList.add(qaDto.toModelObject()));
            return questionAnswersList;
        } catch (Exception ex) {
            throw new QuestionReadException("Ошибка чтения csv файла.", ex);
        }
    }
}