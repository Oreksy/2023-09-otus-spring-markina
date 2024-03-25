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
//import ru.otus.hw.dto.AuthorInsertDto;
//import ru.otus.hw.services.AuthorService;
//
//import java.util.List;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@DisplayName("Author контроллер")
//@WebMvcTest(AuthorController.class)
//public class AuthorControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @MockBean
//    private AuthorService authorService;
//
//    @DisplayName("должен отображать список всех авторов")
//    @Test
//    void shouldReturnCorrectAuthorList() throws Exception {
//
//        List<AuthorDto> authors = List.of(new AuthorDto(1L, "Author_1"),
//                new AuthorDto(2L, "Author_2"),
//                new AuthorDto(3L, "Author_3"));
//
//        when(authorService.findAll()).thenReturn(authors);
//
//
//        this.mvc.perform(get("/api/v1/authors"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json(objectMapper.writeValueAsString(authors)));
//
//        verify(authorService, times(1)).findAll();
//    }
//
//    @DisplayName("должен отображать автора для редактирования")
//    @Test
//    void shouldReturnCorrectAuthor() throws Exception {
//
//        AuthorDto author = new AuthorDto(1L, "Author_1");
//
//        when(authorService.findById(1L)).thenReturn(author);
//
//        this.mvc.perform(get("/api/v1/authors/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().string(containsString("Author_1")));
//    }
//
//    @DisplayName("должен редактировать автора")
//    @Test
//    void shouldReturnCorrectEditAuthor() throws Exception {
//
//        AuthorDto newAuthor = new AuthorDto(1L, "newAuthor_1");
//
//        AuthorDto expectedAuthor = new AuthorDto(1L, "newAuthor_1");
//
//        when(authorService.update(any(Long.class),any(String.class))).thenReturn(expectedAuthor);
//
//        this.mvc.perform(put("/api/v1/authors/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newAuthor)))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(expectedAuthor)));
//
//        verify(authorService, times(1)).update(any(Long.class),any(String.class));
//    }
//
//    @DisplayName("должен создавать автора")
//    @Test
//    void shouldReturnCorrectCreateAuthor() throws Exception {
//
//        AuthorInsertDto authorInsertDto = new AuthorInsertDto("newAuthor_1");
//
//        AuthorDto expectedAuthor = new AuthorDto(0L, "newAuthor_1");
//
//        when(authorService.insert(any(AuthorInsertDto.class))).thenReturn(expectedAuthor);
//
//        this.mvc.perform(post("/api/v1/authors")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(authorInsertDto)))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(expectedAuthor)));
//
//        verify(authorService, times(1)).insert(any(AuthorInsertDto.class));
//
//    }
//
//    @DisplayName("должен удалять автора по id")
//    @Test
//    void shouldDeleteAuthor() throws Exception {
//        this.mvc.perform(delete("/api/v1/authors/1"))
//                .andExpect(status().isOk());
//
//        verify(authorService,times(1) ).deleteById(1L);
//    }
//}