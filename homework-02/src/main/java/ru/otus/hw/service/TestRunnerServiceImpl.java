package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.model.Student;
import ru.otus.hw.model.TestResult;

@RequiredArgsConstructor
@Service
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    private final IOService ioService;

    @Override
    public void run() {
        try {
            Student student = studentService.determineCurrentStudent();
            TestResult testResult = testService.executeTestFor(student);
            resultService.showResult(testResult);
        } catch (QuestionReadException qex) {
            ioService.printLine("Error reading questions.");
        } catch (IllegalArgumentException iex) {
            ioService.printLine("Invalid answer input.");
        } catch (Exception ex) {
            ioService.printLine("Received an unknown error.");
        }
    }
}
