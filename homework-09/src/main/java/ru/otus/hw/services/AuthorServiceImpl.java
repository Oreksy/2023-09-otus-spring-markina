package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.AuthorMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    private final AuthorMapper authorMapper;

    @Override
    public List<AuthorDto> findAll() {
        return authorRepository.findAll().stream().map(authorMapper::modelToDto).collect(Collectors.toList());
    }

    @Override
    public AuthorDto findById(long id) {
        return authorRepository.findById(id).map(authorMapper::modelToDto)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(id)));
    }

    @Transactional
    @Override
    public Author insert(String fullName) {
        Author author = new Author(0,fullName);
        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        List<Book> booksByAuthorId =  bookRepository.findAllByAuthorId(id);
        if (booksByAuthorId.isEmpty()) {
            authorRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(
                    "Could not delete the author with id = %d, author has books.".formatted(id));
        }
    }

    @Transactional
    @Override
    public Author update(long id, String fullName) {
        Author author = new Author(id, fullName);
        return authorRepository.save(author);
    }
}
