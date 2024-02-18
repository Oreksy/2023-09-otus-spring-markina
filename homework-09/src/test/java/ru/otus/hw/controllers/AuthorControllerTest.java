package ru.otus.hw.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.services.AuthorService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Author контроллер")
@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @DisplayName("должен отображать список всех авторов")
    @Test
    void shouldReturnCorrectAuthorList() throws Exception {

        List<AuthorDto> authors = List.of(new AuthorDto(1L, "Author_1"),
                new AuthorDto(2L, "Author_2"),
                new AuthorDto(3L, "Author_3"));

        when(authorService.findAll()).thenReturn(authors);


        this.mvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Author_1")))
                .andExpect(content().string(containsString("Author_2")))
                .andExpect(content().string(containsString("Author_3")));

    }

    @DisplayName("должен отображать автора для редактирования")
    @Test
    void shouldReturnCorrectAuthor() throws Exception {

        AuthorDto author = new AuthorDto(1L, "Author_1");

        when(authorService.findById(1L)).thenReturn(author);

        this.mvc.perform(get("/authors/edit/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Author_1")));
    }

    @DisplayName("должен редактировать автора")
    @Test
    void shouldReturnCorrectEditAuthor() throws Exception {

        AuthorDto newAuthor = new AuthorDto(1L, "newAuthor_1");

        this.mvc.perform(post("/authors/edit")
                        .flashAttr("author", newAuthor))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/authors"));

        verify(authorService, times(1)).update(newAuthor.getId(), newAuthor.getFullName());
    }

    @DisplayName("должен создавать автора")
    @Test
    void shouldReturnCorrectCreateAuthor() throws Exception {

        AuthorDto newAuthor = new AuthorDto(0L, "newAuthor_1");

        this.mvc.perform(post("/authors/edit")
                        .flashAttr("author", newAuthor))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/authors"));

        verify(authorService, times(1)).update(newAuthor.getId(), newAuthor.getFullName());
    }

    @DisplayName("должен удалять автора")
    @Test
    void shouldDeleteAuthor() throws Exception {
        this.mvc.perform(post("/authors/delete/1"))
                .andExpect(status().is(302));

        verify(authorService).deleteById(1L);
    }
}
