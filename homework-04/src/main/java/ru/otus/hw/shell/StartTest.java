package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.service.TestRunnerService;

@ShellComponent
@RequiredArgsConstructor
public class StartTest {

    private final TestRunnerService testRunnerService;

    @ShellMethod(value = "Start testing", key = {"s", "start"})
    public void start() {
        testRunnerService.run();
    }
}

