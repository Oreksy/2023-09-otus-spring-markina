package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.sql.SQLException;

@RequiredArgsConstructor
@ShellComponent
public class ConsoleCommands {
    @ShellMethod(value = "Show db console", key = "scon")
    public void showDbConsole() throws SQLException {
        Console.main();
    }
}
