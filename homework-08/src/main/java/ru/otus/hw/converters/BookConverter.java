package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Book;

@RequiredArgsConstructor
@Component
public class BookConverter {
    private final AuthorConverter authorConverter;


    public String bookToString(Book book) {
        return "Id: %s, title: %s, author: {%s}, genres: [%s]".formatted(
                book.getId(),
                book.getTitle(),
                authorConverter.authorToString(book.getAuthor()),
                book.getGenre());
    }
}
