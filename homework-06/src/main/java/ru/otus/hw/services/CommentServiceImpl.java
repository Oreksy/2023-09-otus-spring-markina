package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl  implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }


    @Transactional(readOnly = true)
    @Override
    public List<Comment> findByBookId(long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Book with id = %s not found", bookId)));
        return commentRepository.findByBookId(bookId);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Comment insert(String context, long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Book with id = %s not found", bookId)));
        return commentRepository.save(new Comment(0, context, book));
    }

    @Transactional
    @Override
    public Comment update(long id, String context) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Comment with id = %s not found", id)));
        comment.setContext(context);
        return commentRepository.save(comment);
    }
 }
