package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.exceptions.FileNotFindException;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.model.Question;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {

        try (InputStream inputStream = this.getClass().getResourceAsStream(fileNameProvider.getTestFileName())) {
            if (inputStream == null) {
                throw new FileNotFindException("File did not find.");
            }
            InputStreamReader reader = new InputStreamReader(inputStream);

            CsvToBean<QuestionDto> question = new CsvToBeanBuilder<QuestionDto>(reader)
                    .withType(QuestionDto.class)
                    .withSeparator(';')
                    .build();
            return question.parse().stream()
                    .map(QuestionDto::toModelObject)
                    .toList();

        } catch (FileNotFindException exfn) {
            throw exfn;
        } catch (Exception ex) {
           throw new QuestionReadException("Error reading csv file.", ex);
        }
    }
}
