package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.GenreMapper;
import ru.otus.hw.repositories.GenreRepository;

@RequiredArgsConstructor
@RestController
public class GenreController {

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    @GetMapping({"/api/v1/genres"})
    public Flux<GenreDto> getAllGenres() {
        return genreRepository.findAll().map(genreMapper::modelToDto);
    }


    @GetMapping("/api/v1/genres/{id}")
    public Mono<GenreDto> getGenreById(@PathVariable("id") String id) {
        return genreRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Genre with id %s not found".formatted(id))))
                .map(genreMapper::modelToDto);
    }

    @PostMapping("/api/v1/genres")
    public Mono<GenreDto> insertGenre(@Valid @RequestBody GenreDto genreDto) {
        return genreRepository.save(genreMapper.toModel(genreDto)).map(genreMapper::modelToDto);
    }

     @PutMapping("/api/v1/genres/{id}")
    public Mono<GenreDto> updateGenre(@RequestBody @Valid GenreDto genreDto) {
        return genreRepository.save(genreMapper.toModel(genreDto)).map(genreMapper::modelToDto);
    }
}
