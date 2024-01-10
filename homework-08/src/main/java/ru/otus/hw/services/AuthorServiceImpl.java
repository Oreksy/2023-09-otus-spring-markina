package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(String id) {
        return authorRepository.findById(id);
    }

    @Transactional
    @Override
    public Author insert(String fullName) {
        Author author = new Author(fullName);
        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        List<Book> booksByAuthorId =  bookRepository.findAllByAuthorId(id);
        if (booksByAuthorId.isEmpty()) {
            authorRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Could not delete the author with id = %s".formatted(id));
        }
    }

    @Transactional
    @Override
    public Author update(String id, String fullName) {
        Author author = new Author(id, fullName);
        return authorRepository.save(author);
    }
}
