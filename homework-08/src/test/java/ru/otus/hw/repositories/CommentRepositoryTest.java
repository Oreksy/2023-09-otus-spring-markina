package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Mongo для работы с комментариями ")
@DataMongoTest
public class CommentRepositoryTest {

    private static final String FIRST_COMMENT_ID = "1";

    private static final String FIRST_BOOK_ID = "1";

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName("должен загружать комментарии по id книги")
    @Test
    void shouldReturnCorrectCommentByBookId() {

        Comment expectedComment = mongoTemplate.findById(FIRST_COMMENT_ID, Comment.class);

        val comments = commentRepository.findByBookId(FIRST_BOOK_ID);

        assertThat(comments.get(0)).isNotNull().matches(a -> a.getContext().equals(expectedComment.getContext()))
                .matches(a-> a.getId().equals(expectedComment.getId()))
                .matches(a-> a.getBook().getTitle().equals(expectedComment.getBook().getTitle()));

    }

    @DisplayName("должен загружать комментарии по книге")
    @Test
    void shouldReturnCorrectCommentByBook() {

        Comment expectedComment = mongoTemplate.findById(FIRST_COMMENT_ID, Comment.class);

        Book book = mongoTemplate.findById(FIRST_BOOK_ID, Book.class);

        val comments = commentRepository.findByBook(book);

        assertThat(comments.get(0)).isNotNull().matches(a -> a.getContext().equals(expectedComment.getContext()))
                .matches(a-> a.getId().equals(expectedComment.getId()))
                .matches(a-> a.getBook().getTitle().equals(expectedComment.getBook().getTitle()));

    }
}
