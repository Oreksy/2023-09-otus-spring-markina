package ru.otus.hw.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.controllers.GenreController;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.mappers.GenreMapper;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@DisplayName("Genre контроллер")
@WebFluxTest
@ContextConfiguration(classes = {GenreController.class, GenreMapper.class})
public class GenreControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GenreRepository genreRepository;

    private final List<GenreDto> genreDtos = new ArrayList<>();

    @Autowired
    private GenreMapper genreMapper;

    @BeforeEach
    void setUp() {
        genreDtos.add(new GenreDto("1", "Genre_1"));
        genreDtos.add(new GenreDto("2", "Genre_2"));
        genreDtos.add(new GenreDto("3", "Genre_3"));
    }


    @DisplayName("должен отображать список всех жанров")
    @Test
    void shouldReturnCorrectGenreList() {

        when(genreRepository.findAll()).thenReturn(Flux.fromStream(genreDtos.stream().map(genreMapper::toModel)));

        webTestClient.get().uri("/api/v1/genres")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(GenreDto.class)
                .hasSize(3)
                .isEqualTo(genreDtos);
        verify(genreRepository, times(1)).findAll();
    }

    @DisplayName("должен находить жанр по идентификатору")
    @Test
    void shouldReturnCorrectGenre() {


        when(genreRepository.findById(genreDtos.get(0).getId()))
                .thenReturn(Mono.just(genreMapper.toModel(genreDtos.get(0))));

        webTestClient.get().uri("/api/v1/genres/{id}", genreDtos.get(0).getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(GenreDto.class)
                .isEqualTo(genreDtos.get(0));

        verify(genreRepository, times(1)).findById(genreDtos.get(0).getId());

    }

    @DisplayName("должен создавать жанр")
    @Test
    void shouldReturnCorrectCreateGenre() {

        GenreDto newGenreDto = new GenreDto(null, "Genre_4");

        when(genreRepository.save(any(Genre.class))).thenReturn(Mono.just(genreMapper.toModel(newGenreDto)));

        webTestClient.post().uri("/api/v1/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(newGenreDto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(GenreDto.class)
                .isEqualTo(newGenreDto);

        verify(genreRepository, times(1)).save(any(Genre.class));

    }

    @DisplayName("должен редактировать жанр")
    @Test
    void shouldReturnCorrectEditGenre() {

        GenreDto updateGenre = new GenreDto("3", "newGenre_33");

        when(genreRepository.save(any(Genre.class))).thenReturn(Mono.just(genreMapper.toModel(updateGenre)));

        webTestClient.put().uri("/api/v1/genres/{id}", updateGenre)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(updateGenre))
                .exchange()
                .expectStatus().isOk()
                .expectBody(GenreDto.class)
                .isEqualTo(updateGenre);

        verify(genreRepository, times(1)).save(any(Genre.class));
    }
}
