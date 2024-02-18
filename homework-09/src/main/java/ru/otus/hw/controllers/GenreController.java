package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.GenreService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class GenreController {

    private final GenreService genreService;

    @GetMapping({"/genres"})
    public String getAllGenres(Model model) {
        List<GenreDto> genres = genreService.findAll();
        model.addAttribute("genres", genres);
        return "genrelist";
    }

    @GetMapping("/genres/edit/{id}")
    public String editGenre(@PathVariable("id") long id, Model model) {
        GenreDto genre = genreService.findById(id);
        model.addAttribute("genre", genre);
        return "genre-edit";
    }
}
