package ru.otus.hw.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.controllers.BookController;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.mappers.AuthorMapper;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.mappers.GenreMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@DisplayName("Book контроллер")
@WebFluxTest
@ContextConfiguration(classes = {BookController.class, BookMapper.class, AuthorMapper.class, GenreMapper.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private BookController bookController;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private GenreMapper genreMapper;

    private static List<BookDto> booksDto = new ArrayList<>();

    private static List<Book> books = new ArrayList<>();


    @BeforeAll
    void setBooks() {
        books = List.of(
                new Book("1", "BookTitle_1",
                        new Author("1", "Author_1"),
                        new Genre("1", "Genre_1")),
                new Book("2", "BookTitle_2",
                        new Author("2", "Author_2"),
                        new Genre("2", "Genre_2")),
                new Book("3", "BookTitle_3",
                        new Author("3", "Author_3"),
                        new Genre("3", "Genre_3"))
        );
        booksDto = books.stream()
                .map(bookMapper::modelToDto)
                .toList();
    }


    @DisplayName("должен отображать список всех авторов")
    @Test
    void shouldReturnCorrectAuthorList() {

        when(bookRepository.findAll()).thenReturn(Flux.fromIterable(books));

        webTestClient.get().uri("/api/v1/books")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(BookDto.class)
                .hasSize(3)
                .isEqualTo(booksDto);

        verify(bookRepository, times(1)).findAll();
    }

    @DisplayName("должен отображать книгу для редактирования")
    @Test
    void shouldReturnCorrectBook() {

        BookDto expectedBook = new BookDto("1", "BookTitle_1", new AuthorDto("1", "Author_1"),
                new GenreDto("1", "Genre_1"));

        when(bookRepository.findById(expectedBook.getId())).thenReturn(Mono.just(bookMapper.toModel(expectedBook)));

        webTestClient.get().uri("/api/v1/books/{id}", expectedBook.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BookDto.class)
                .isEqualTo(expectedBook);

        verify(bookRepository, times(1)).findById(expectedBook.getId());
    }

    @DisplayName("должен создавать книгу")
    @Test
    void shouldReturnCorrectCreateBook() {

        BookDto expectedBook = new BookDto(null, "BookTitle_44", new AuthorDto("1", "Author_1"),
                new GenreDto("1", "Genre_1"));

        when(authorRepository.findById(any(String.class)))
                .thenReturn(Mono.just(authorMapper.toModel(expectedBook.getAuthor())));
        when(genreRepository.findById(any(String.class)))
                .thenReturn(Mono.just(genreMapper.toModel(expectedBook.getGenre())));
        when(bookRepository.save(any(Book.class))).thenReturn(Mono.just(bookMapper.toModel(expectedBook)));

        webTestClient.post().uri("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(expectedBook))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .isEqualTo(expectedBook);

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @DisplayName("должен редактировать книгу по id")
    @Test
    void shouldReturnCorrectEditBook() throws Exception {

        BookDto expectedBook = new BookDto("2", "BookTitle_222", new AuthorDto("1", "Author_1"),
                new GenreDto("1", "Genre_1"));

        when(authorRepository.findById(any(String.class)))
                .thenReturn(Mono.just(authorMapper.toModel(expectedBook.getAuthor())));
        when(genreRepository.findById(any(String.class)))
                .thenReturn(Mono.just(genreMapper.toModel(expectedBook.getGenre())));
        when(bookRepository.save(any(Book.class))).thenReturn(Mono.just(bookMapper.toModel(expectedBook)));

        webTestClient.post().uri("/api/v1/books", expectedBook.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(expectedBook))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .isEqualTo(expectedBook);

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @DisplayName("должен удалять книгу по id")
    @Test
    void shouldDeleteBook() throws Exception {

        when(bookRepository.deleteById(booksDto.get(0).getId())).thenReturn(Mono.empty());

        webTestClient.delete().uri("/api/v1/books/{id}", booksDto.get(0).getId())
                .exchange()
                .expectStatus().isNoContent()
                .expectBody(Void.class);

        verify(bookRepository,times(1) ).deleteById(booksDto.get(0).getId());

    }

}
