package service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.model.Student;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.StudentServiceImpl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class StudentServiceImplTest {

    @DisplayName("Ввод студента")
    @Test
    void determineCurrentStudentSuccess() {
        IOService ioService = Mockito.mock(IOService.class);
        StudentServiceImpl studentService = new StudentServiceImpl(ioService);
        when(ioService.readStringWithPrompt("Please input your first name")).thenReturn("Valentina");
        when(ioService.readStringWithPrompt("Please input your last name")).thenReturn("Markina");
        Student student = studentService.determineCurrentStudent();
        assertEquals("Valentina", student.firstName());
        assertEquals("Markina", student.lastName());
    }

}
