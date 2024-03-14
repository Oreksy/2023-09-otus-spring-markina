package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.AuthorMapper;
import ru.otus.hw.repositories.AuthorRepository;


@RequiredArgsConstructor
@RestController
public class AuthorController {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @GetMapping({"/api/v1/authors"})
    public Flux<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().map(authorMapper::modelToDto);
    }

    @GetMapping("/api/v1/authors/{id}")
    public Mono<AuthorDto> getAuthorById(@PathVariable("id") String id) {
        return authorRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Author with id %s not found".formatted(id))))
                .map(authorMapper::modelToDto);
    }

    @PostMapping("/api/v1/authors")
    public Mono<AuthorDto> insertAuthor(@Valid @RequestBody AuthorDto authorDto) {
        return authorRepository.save(authorMapper.toModel(authorDto)).map(authorMapper::modelToDto);
    }

    @PutMapping("/api/v1/authors/{id}")
    public Mono<AuthorDto> updateAuthor(@RequestBody @Valid AuthorDto authorDto) {
        return authorRepository.save(authorMapper.toModel(authorDto)).map(authorMapper::modelToDto);
    }

    @DeleteMapping("/api/v1/authors/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAuthor(@PathVariable("id") String id) {
        return authorRepository.deleteById(id);
    }
}


