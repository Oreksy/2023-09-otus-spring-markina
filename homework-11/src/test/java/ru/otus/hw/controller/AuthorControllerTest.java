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
import reactor.core.publisher.Flux;
import ru.otus.hw.controllers.AuthorController;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.mappers.AuthorMapper;
import ru.otus.hw.repositories.AuthorRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@DisplayName("Author контроллер")
@WebFluxTest
@ContextConfiguration(classes = {AuthorController.class, AuthorMapper.class})
public class AuthorControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorController authorController;

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
    void shouldReturnCorrectAuthorList() throws Exception {


        when(authorRepository.findAll()).thenReturn(Flux.fromStream(authorsDto.stream().map(authorMapper::toModel)));

        webTestClient.get().uri("/api/v1/authors")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorDto.class)
                .isEqualTo(authorsDto);

    }
}
