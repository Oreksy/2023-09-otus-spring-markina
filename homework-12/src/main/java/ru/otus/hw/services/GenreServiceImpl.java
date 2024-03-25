package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.GenreMapper;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    @Override
    public List<GenreDto> findAll() {
        return genreRepository.findAll().stream().map(genreMapper::modelToDto).collect(Collectors.toList());
    }

    @Override
    public GenreDto findById(long id) {
        return  genreRepository.findById(id).map(genreMapper::modelToDto)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %d not found".formatted(id)));
    }

    @Transactional
    @Override
    public GenreDto update(long id, String name) {
        Genre genre = new Genre(id, name);
        return genreMapper.modelToDto(genreRepository.save(genre));
    }
}
