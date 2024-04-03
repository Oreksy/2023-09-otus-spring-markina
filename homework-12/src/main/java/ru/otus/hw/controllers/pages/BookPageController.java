package ru.otus.hw.controllers.pages;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class BookPageController {
    @GetMapping({"/books"})
    public String getAllBooks() {
        return "booklist";
    }

    @GetMapping("/books/edit/{id}")
    public String editBook(@PathVariable("id") long id) {
        return "book-edit";
    }
}