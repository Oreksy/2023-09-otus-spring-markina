//package ru.otus.hw.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import ru.otus.hw.dto.AuthorDto;
//import ru.otus.hw.dto.BookDto;
//import ru.otus.hw.dto.GenreDto;
//import ru.otus.hw.services.AuthorService;
//import ru.otus.hw.services.BookService;
//import ru.otus.hw.services.GenreService;
//
//import java.util.List;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@DisplayName("Book контроллер")
//@WebMvcTest(BookController.class)
//public class BookControllerTest {
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//    @MockBean
//    private BookService bookService;
//
//    @MockBean
//    private AuthorService authorService;
//
//    @MockBean
//    private GenreService genreService;
//
//
//    @DisplayName("должен отображать список всех книг")
//    @Test
//    void shouldReturnCorrectBooksList() throws Exception {
//        List<BookDto> books = List.of(new BookDto(1L, "BookTitle_1", new AuthorDto(1L, "Author_1"),
//                new GenreDto(1L, "Genre_1")),
//        new BookDto(2L, "BookTitle_2", new AuthorDto(2L, "Author_2"),
//                new GenreDto(2L, "Genre_2")),
//        new BookDto(3L, "BookTitle_3", new AuthorDto(3L, "Author_3"),
//                new GenreDto(3L, "Genre_3")));
//        when(bookService.findAll()).thenReturn(books);
//
//        this.mvc.perform(get("/api/v1/books"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json(objectMapper.writeValueAsString(books)));
//
//    }
//
//    @DisplayName("должен отображать книгу для редактирования")
//    @Test
//    void shouldReturnCorrectBook() throws Exception {
//
//        BookDto expectedBook = new BookDto(1L, "BookTitle_1", new AuthorDto(1L, "Author_1"),
//                new GenreDto(1L, "Genre_1"));
//
//        when(bookService.findById(expectedBook.getId())).thenReturn(expectedBook);
//
//        this.mvc.perform(get("/api/v1/books/{id}", expectedBook.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json(objectMapper.writeValueAsString(expectedBook)));
//    }
//
//    @DisplayName("должен редактировать книгу")
//    @Test
//    void shouldReturnCorrectEditBook() throws Exception {
//
//        BookDto updateBook = new BookDto(1L, "newBookTitle_1", new AuthorDto(1L, "Author_1"),
//                new GenreDto(1L, "Genre_1"));
//
//        BookDto expectedBook = new BookDto(1L, "newBookTitle_1", new AuthorDto(1L, "Author_1"),
//                new GenreDto(1L, "Genre_1"));
//
//        when(bookService.update(any(Long.class),any(String.class),any(Long.class),any(Long.class))).thenReturn(expectedBook);
//
//        this.mvc.perform(put("/api/v1/books/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(updateBook)))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(expectedBook)));
//
//        verify(bookService, times(1)).update(any(Long.class),any(String.class)
//                ,any(Long.class),any(Long.class));
//    }
//
//    @DisplayName("должен создавать книгу")
//    @Test
//    void shouldReturnCorrectCreateBook() throws Exception {
//
//        BookDto newBook = new BookDto(0L, "newBookTitle_1", new AuthorDto(1L, "Author_1"),
//                new GenreDto(1L, "Genre_1"));
//
//        BookDto expectedBook = new BookDto(0L, "newBookTitle_1", new AuthorDto(1L, "Author_1"),
//                new GenreDto(1L, "Genre_1"));
//
//        when(bookService.update(any(Long.class),any(String.class),any(Long.class),any(Long.class))).thenReturn(expectedBook);
//
//        this.mvc.perform(post("/api/v1/books")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newBook)))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(expectedBook)));
//
//        verify(bookService, times(1)).update(any(Long.class),any(String.class)
//                ,any(Long.class),any(Long.class));
//    }
//
//
//    @DisplayName("должен удалять книгу по id")
//    @Test
//    void shouldDeleteBook() throws Exception {
//        this.mvc.perform(delete("/api/v1/books/1"))
//                .andExpect(status().isOk());
//
//        verify(bookService).deleteById(1L);
//    }
//}
