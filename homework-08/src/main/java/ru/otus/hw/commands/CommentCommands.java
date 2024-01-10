package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.services.CommentService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    //cid 1
    @ShellMethod(value = "Find comment by id", key = "cid")
    public String findCommentById(String id) {
        return commentService.findById(id)
                .map(commentConverter::commentToString)
                .orElse("Comment with id %s not found".formatted(id));
    }

    //cbid 1
    @ShellMethod(value = "Find comment by book id", key = "cbid")
    public String findCommentByBookId(String bookId) {
        return commentService.findByBookId(bookId).stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    //cbb 2
    @ShellMethod(value = "Find comment by book id", key = "cbb")
    public String findCommentByBook(String bookId) {
        return commentService.findByBookId(bookId).stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Delete Comment by id", key = "cdel")
    public void deleteComment(String id) {
        commentService.deleteById(id);
    }

    //cins "New comment" 3
    @ShellMethod(value = "Insert comment", key = "cins")
    public String insertComment(String context, String bookId) {
        var saveComment = commentService.insert(context, bookId);
        return commentConverter.commentToString(saveComment);
    }

    //cupd 3 "Change comment"
    @ShellMethod(value = "Update comment", key = "cupd")
    public String updateComment(String id, String context) {
        var saveComment = commentService.update(id, context);
        return commentConverter.commentToString(saveComment);
    }
}
