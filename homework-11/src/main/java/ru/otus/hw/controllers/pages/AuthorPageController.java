package ru.otus.hw.controllers.pages;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class AuthorPageController {
    @GetMapping({"/authors"})
    public String getAllAuthors() {
        return "authorlist";
    }

    @GetMapping("/authors/edit/{id}")
    public String editAuthor(@PathVariable("id") String id, Model model) {
        return "author-edit";
    }

    @GetMapping("/authors/add")
    public String addAuthor() {
        return "author-add";
    }
}
