package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.model.Student;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = StudentServiceImpl.class)
public class StudentServiceImplTest {

    @MockBean
    private LocalizedIOService ioService;

    @Autowired
    private StudentServiceImpl studentService;

    @DisplayName("Ввод студента")
    @Test
    void determineCurrentStudentSuccess() {
        when(ioService.readStringWithPromptLocalized("StudentService.input.first.name")).thenReturn("Valentina");
        when(ioService.readStringWithPromptLocalized("StudentService.input.last.name")).thenReturn("Markina");
        Student student = studentService.determineCurrentStudent();
        assertEquals("Valentina", student.firstName());
        assertEquals("Markina", student.lastName());
    }

}
