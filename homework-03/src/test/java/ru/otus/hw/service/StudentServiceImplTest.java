package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.model.Student;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class StudentServiceImplTest {

    @DisplayName("Ввод студента")
    @Test
    void determineCurrentStudentSuccess() {
        LocalizedIOService ioService = Mockito.mock(LocalizedIOService.class);
        StudentServiceImpl studentService = new StudentServiceImpl(ioService);
        when(ioService.readStringWithPromptLocalized("StudentService.input.first.name")).thenReturn("Valentina");
        when(ioService.readStringWithPromptLocalized("StudentService.input.last.name")).thenReturn("Markina");
        Student student = studentService.determineCurrentStudent();
        assertEquals("Valentina", student.firstName());
        assertEquals("Markina", student.lastName());
    }

}
