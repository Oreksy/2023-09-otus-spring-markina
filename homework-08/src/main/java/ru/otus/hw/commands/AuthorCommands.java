package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.AuthorConverter;
import ru.otus.hw.models.Author;
import ru.otus.hw.services.AuthorService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class AuthorCommands {

    private final AuthorService authorService;

    private final AuthorConverter authorConverter;

    @ShellMethod(value = "Find all authors", key = "aa")
    public String findAllAuthors() {
        return authorService.findAll().stream()
                .map(authorConverter::authorToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    // ida 659998092fe3121eebf7a234
    @ShellMethod(value = "Find author by id", key = "ida")
    public String findAuthorById(String id) {
        return authorService.findById(id)
                .map(authorConverter::authorToString)
                .orElse("Author with id %s not found".formatted(id));
    }

    @ShellMethod(value = "Insert author", key = "ina")
    public String insertAuthor(String fullName) {
        Author author = authorService.insert(fullName);
        return authorConverter.authorToString(author);
    }

    @ShellMethod(value = "Delete author by id", key = "dela")
    public void deleteAuthor(String id) {
        authorService.deleteById(id);
    }

    @ShellMethod(value = "Update author", key = "upda")
    public String updateAuthor(String id, String fullName) {
        Author author = authorService.update(id, fullName);
        return authorConverter.authorToString(author);
    }
}
