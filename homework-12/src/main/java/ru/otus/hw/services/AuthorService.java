package ru.otus.hw.services;

import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.AuthorInsertDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();

    AuthorDto findById(long id);

    AuthorDto insert(AuthorInsertDto fullName);

    void deleteById(long id);

    AuthorDto update(long id, String fullName);
}
