package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookMapper bookMapper;

    @GetMapping({"/api/v1/books"})
    public Flux<BookDto> getAllBooks() {
        return bookRepository.findAll().map(bookMapper::modelToDto);
    }


    @GetMapping("/api/v1/books/{id}")
    public Mono<BookDto> getAuthorById(@PathVariable("id") String id) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Book with id %s not found".formatted(id))))
                .map(bookMapper::modelToDto);
    }

    @PutMapping("/api/v1/books/{id}")
    public Mono<BookDto> updateBook(@RequestBody @Valid BookDto bookDto) {
        return monoSave(bookDto.getId(),bookDto.getTitle(),bookDto.getAuthor().getId(),bookDto.getGenre().getId());
    }



    @PostMapping("/api/v1/books")
    public Mono<BookDto> insertBook(@Valid @RequestBody BookDto bookDto) {
        return monoSave(bookDto.getId(),bookDto.getTitle(),bookDto.getAuthor().getId(),bookDto.getGenre().getId());
    }

    @DeleteMapping("/api/v1/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBook(@PathVariable("id") String id) {
         return bookRepository.deleteById(id);
    }


    private Mono<BookDto> monoSave(String id, String title, String authorId, String genreId) {
        return authorRepository.findById(authorId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Author with id %s not found".formatted(id))))
                .zipWith(genreRepository.findById(genreId))
                .switchIfEmpty(Mono.error(new EntityNotFoundException("genre with id %s not found".formatted(id))))
                .flatMap(data -> {
                    return bookRepository.save((new Book(id,title,data.getT1(),data.getT2())))
                            .map(bookMapper::modelToDto); });
    }
}
