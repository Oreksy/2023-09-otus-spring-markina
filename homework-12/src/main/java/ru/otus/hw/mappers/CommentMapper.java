package ru.otus.hw.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Comment;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final BookMapper bookMapper;

    public CommentDto modelToDto(Comment comment) {
        return new CommentDto(comment.getId(),
                comment.getContext(),
                bookMapper.modelToDto(comment.getBook()));
    }
}
