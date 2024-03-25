package ru.otus.hw.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Book;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final AuthorMapper authorMapper;

    private final GenreMapper genreMapper;

    public  BookDto modelToDto(Book book) {
        return new BookDto(book.getId(),
                book.getTitle(),
                authorMapper.modelToDto(book.getAuthor()),
                genreMapper.modelToDto(book.getGenre()));
    }

    public Book toModel (BookDto bookDto) {
        return new Book(bookDto.getId(),
                bookDto.getTitle(),
                authorMapper.toModel(bookDto.getAuthor()),
                genreMapper.toModel(bookDto.getGenre())
                );
    }
}
