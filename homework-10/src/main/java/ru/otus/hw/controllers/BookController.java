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
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping({"/api/v1/books"})
    public List<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/api/v1/books/{id}")
    public BookDto getAuthorById(@PathVariable("id") long id) {
        return bookService.findById(id);
    }

    @PutMapping("/api/v1/books/{id}")
    public BookDto updateBook(@RequestBody @Valid BookDto bookDto) {
        return bookService.update(bookDto.getId(), bookDto.getTitle(), bookDto.getAuthor().getId(),
                bookDto.getGenre().getId());
    }

    @PostMapping("/api/v1/books")
    public BookDto insertBook(@Valid @RequestBody BookDto bookDto) {
        return bookService.update(bookDto.getId(), bookDto.getTitle(), bookDto.getAuthor().getId(),
                bookDto.getGenre().getId());
    }

    @DeleteMapping("/api/v1/books/{id}")
    public void deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
    }

}
