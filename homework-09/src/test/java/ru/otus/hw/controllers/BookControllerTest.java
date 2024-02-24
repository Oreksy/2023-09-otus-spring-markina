package ru.otus.hw.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Book контроллер")
@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;


    @DisplayName("должен отображать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() throws Exception {
        List<BookDto> books = List.of(new BookDto(1L, "BookTitle_1", new AuthorDto(1L, "Author_1"),
                new GenreDto(1L, "Genre_1")),
        new BookDto(2L, "BookTitle_2", new AuthorDto(2L, "Author_2"),
                new GenreDto(2L, "Genre_2")),
        new BookDto(3L, "BookTitle_3", new AuthorDto(3L, "Author_3"),
                new GenreDto(3L, "Genre_3")));
        when(bookService.findAll()).thenReturn(books);

        this.mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("booklist"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("books"))
                .andExpect(content().string(containsString("BookTitle_1")))
                .andExpect(content().string(containsString("BookTitle_2")))
                .andExpect(content().string(containsString("BookTitle_3")));

    }

    @DisplayName("должен отображать книгу для редактирования")
    @Test
    void shouldReturnCorrectBook() throws Exception {

        BookDto book = new BookDto(1L, "BookTitle_1", new AuthorDto(1L, "Author_1"),
                new GenreDto(1L, "Genre_1"));

        when(bookService.findById(1L)).thenReturn(book);
        when(authorService.findAll()).thenReturn(List.of(
                new AuthorDto(1L, "Author_1"),
                new AuthorDto(2L, "Author_2"),
                new AuthorDto(3L, "Author_3")
        ));
        when(genreService.findAll()).thenReturn(List.of(
                new GenreDto(1L, "Genre_1"),
                new GenreDto(2L, "Genre_1"),
                new GenreDto(3L, "Genre_1")
        ));

        this.mvc.perform(get("/books/edit/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("book-edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("book"))
                .andExpect(content().string(containsString("BookTitle_1")))
                .andExpect(content().string(containsString("<option value=\"1\" selected=\"selected\">Author_1</option>")))
                .andExpect(content().string(containsString("<option value=\"1\" selected=\"selected\">Genre_1</option>")));
    }

    @DisplayName("должен редактировать книгу")
    @Test
    void shouldReturnCorrectEditBook() throws Exception {

        BookDto newBook = new BookDto(1L, "newBookTitle_1", new AuthorDto(1L, "Author_1"),
                new GenreDto(1L, "Genre_1"));

        this.mvc.perform(post("/books/edit")
                .flashAttr("book", newBook))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/books"));

        verify(bookService, times(1)).update(newBook.getId(), newBook.getTitle(),
                newBook.getAuthor().getId(),newBook.getGenre().getId());
    }

    @DisplayName("должен создавать книгу")
    @Test
    void shouldReturnCorrectCreateBook() throws Exception {

        BookDto newBook = new BookDto(0L, "newBookTitle_1", new AuthorDto(1L, "Author_1"),
                new GenreDto(1L, "Genre_1"));

        this.mvc.perform(post("/books/edit")
                        .flashAttr("book", newBook))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/books"));

        verify(bookService, times(1)).update(newBook.getId(), newBook.getTitle(),
                newBook.getAuthor().getId(),newBook.getGenre().getId());
    }

    @DisplayName("должен удалять книгу")
    @Test
    void shouldDeleteBook() throws Exception {
        this.mvc.perform(post("/books/delete/1"))
                .andExpect(status().is(302));

        verify(bookService).deleteById(1L);
    }
}
