package ru.otus.hw.service;

import ru.otus.hw.model.Student;
import ru.otus.hw.model.TestResult;

public interface TestService {
    TestResult executeTestFor(Student student);

}
