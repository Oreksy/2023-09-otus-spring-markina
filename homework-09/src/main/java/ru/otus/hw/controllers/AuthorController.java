package ru.otus.hw.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.AuthorService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping({"/authors"})
    public String getAllAuthors(Model model) {
        List<AuthorDto> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "authorlist";
    }

    @GetMapping("/authors/edit/{id}")
    public String editAuthor(@PathVariable("id") long id, Model model) {
        AuthorDto author = authorService.findById(id);
        model.addAttribute("author", author);
        return "author-edit";
    }

    @PostMapping("/authors/edit")
    public String saveAuthor(@Valid @ModelAttribute("author") AuthorDto author,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "author-edit";
        }
        authorService.update(author.getId(), author.getFullName());
        return "redirect:/authors";
    }

    @GetMapping("/authors/edit")
    public String createAuthor(Model model) {
        model.addAttribute("author",new AuthorDto());
        return "author-edit";
    }

    @PostMapping("/authors/delete/{id}")
    public String deleteAuthor(@PathVariable("id") long id, Model model) {
        try {
            authorService.deleteById(id);
        } catch (EntityNotFoundException ex) {
            model.addAttribute("exception", ex.getMessage());
            var authors = authorService.findAll();
            model.addAttribute("authors", authors);
            return "authorlist";
        }
        return "redirect:/authors";
    }

}
