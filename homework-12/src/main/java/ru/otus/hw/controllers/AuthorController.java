package ru.otus.hw.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.AuthorInsertDto;
import ru.otus.hw.services.AuthorService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping({"/api/v1/authors"})
    public List<AuthorDto> getAllAuthors() {
        return authorService.findAll();
    }

    @GetMapping("/api/v1/authors/{id}")
    public AuthorDto getAuthorById(@PathVariable("id") long id) {
        return authorService.findById(id);
    }

    @PostMapping("/api/v1/authors")
    public AuthorDto insertAuthor(@Valid @RequestBody AuthorInsertDto authorInsertDto) {
        return authorService.insert(authorInsertDto);
    }

    @PutMapping("/api/v1/authors/{id}")
    public AuthorDto updateAuthor(@RequestBody @Valid AuthorDto authorDto) {
        return authorService.update(authorDto.getId(),authorDto.getFullName());
    }

    @DeleteMapping("/api/v1/authors/{id}")
    public void deleteAuthor(@PathVariable("id") long id) {
        authorService.deleteById(id);
    }
}
