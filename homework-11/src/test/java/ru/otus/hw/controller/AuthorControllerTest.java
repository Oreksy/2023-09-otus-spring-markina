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
import ru.otus.hw.controllers.AuthorController;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.mappers.AuthorMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@DisplayName("Author контроллер")
@WebFluxTest
@ContextConfiguration(classes = {AuthorController.class, AuthorMapper.class})
public class AuthorControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AuthorRepository authorRepository;

    private final List<AuthorDto> authorsDto = new ArrayList<>();

    @Autowired
    private AuthorMapper authorMapper;

    @BeforeEach
    void setUp() {
        authorsDto.add(new AuthorDto("1", "Author_1"));
        authorsDto.add(new AuthorDto("2", "Author_2"));
        authorsDto.add(new AuthorDto("3", "Author_3"));
    }


    @DisplayName("должен отображать список всех авторов")
    @Test
    void shouldReturnCorrectAuthorList() {

        when(authorRepository.findAll()).thenReturn(Flux.fromStream(authorsDto.stream().map(authorMapper::toModel)));

        webTestClient.get().uri("/api/v1/authors")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(AuthorDto.class)
                .hasSize(3)
                .isEqualTo(authorsDto);
        verify(authorRepository, times(1)).findAll();
    }

    @DisplayName("должен находить автора по идентификатору")
    @Test
    void shouldReturnCorrectAuthor() {


        when(authorRepository.findById(authorsDto.get(0).getId()))
                .thenReturn(Mono.just(authorMapper.toModel(authorsDto.get(0))));

        webTestClient.get().uri("/api/v1/authors/{id}", authorsDto.get(0).getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AuthorDto.class)
                .isEqualTo(authorsDto.get(0));

        verify(authorRepository, times(1)).findById(authorsDto.get(0).getId());

    }

    @DisplayName("должен создавать автора")
    @Test
    void shouldReturnCorrectCreateAuthor() {

        AuthorDto newAuthorDto = new AuthorDto(null, "Author_4");

        when(authorRepository.save(any(Author.class))).thenReturn(Mono.just(authorMapper.toModel(newAuthorDto)));

        webTestClient.post().uri("/api/v1/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(newAuthorDto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthorDto.class)
                .isEqualTo(newAuthorDto);

        verify(authorRepository, times(1)).save(any(Author.class));

    }

    @DisplayName("должен редактировать автора")
    @Test
    void shouldReturnCorrectEditAuthor() {

        AuthorDto updateAuthor = new AuthorDto("2", "newAuthor_22");

        when(authorRepository.save(any(Author.class))).thenReturn(Mono.just(authorMapper.toModel(updateAuthor)));

        webTestClient.put().uri("/api/v1/authors/{id}", updateAuthor)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(updateAuthor))
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthorDto.class)
                .isEqualTo(updateAuthor);

        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @DisplayName("должен удалять автора по id")
    @Test
    void shouldDeleteAuthor() {

        when(authorRepository.deleteById(authorsDto.get(2).getId())).thenReturn(Mono.empty());

        webTestClient.delete().uri("/api/v1/authors/{id}", authorsDto.get(2).getId())
                .exchange()
                .expectStatus().isNoContent()
                .expectBody(Void.class);


        verify(authorRepository,times(1) ).deleteById(authorsDto.get(2).getId());
    }

}
