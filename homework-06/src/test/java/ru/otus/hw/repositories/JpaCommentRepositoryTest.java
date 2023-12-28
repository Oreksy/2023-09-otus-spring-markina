package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями ")
@DataJpaTest
@Import({JpaCommentRepository.class})
public class JpaCommentRepositoryTest {

    @Autowired
    private JpaCommentRepository repositoryJpa;

    @Autowired
    private TestEntityManager em;

    private static final long FIRST_COMMENT_ID = 1L;

    private static final long FIRST_BOOK_ID = 1L;

    private static final String COMMENT_NEW = "Comment_5000";

    private static final int EXPECTED_NUMBER_OF_COMMENT = 3;


    @DisplayName("должен загружать комментарий по id")
    @Test
    void shouldReturnCorrectCommentById() {
        var actualComment = repositoryJpa.findById(FIRST_COMMENT_ID);
        var expectedComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(actualComment).isPresent()
                .get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("должен загружать комментарии по Id книги")
    @Test
    void shouldReturnCorrectCommentByBookId() {

        val comments = repositoryJpa.findByBookId(FIRST_BOOK_ID);
        assertThat(comments).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENT)
                .allMatch(c -> !c.getContext().isEmpty())
                .allMatch(c -> c.getBook() != null);
    }

    @DisplayName("должен удалять комментарий по id ")
    @Test
    void shouldDeleteComment() {
        val firstComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(firstComment).isNotNull();
        em.detach(firstComment);

        repositoryJpa.deleteById(FIRST_COMMENT_ID);
        val deletedComment = em.find(Comment.class, FIRST_BOOK_ID);

        assertThat(deletedComment).isNull();
    }

    @DisplayName("должен сохранять измененный комментарий")
    @Test
    void shouldSaveUpdatedComment() {
        val firstComment = em.find(Comment.class, FIRST_COMMENT_ID);
        String oldContext = firstComment.getContext();
        em.detach(firstComment);

        firstComment.setContext(COMMENT_NEW);
        repositoryJpa.save(firstComment);

        val updateComment = em.find(Comment.class, FIRST_COMMENT_ID);

        assertThat(updateComment.getContext()).isNotEqualTo(oldContext).isEqualTo(COMMENT_NEW);

    }

    @DisplayName("должен корректно сохранять новый комментарий")
    @Test
    void shouldSaveInsertedComment() {

        Author author = new Author(1,"Author_1");
        Genre genre = new Genre(1,"Genre_1");
        Book book = new Book(1,"BookTitle_1",author,genre);

        Comment comment = new Comment(0, COMMENT_NEW, book);
        repositoryJpa.save(comment);
        assertThat(comment.getId()).isGreaterThan(0);

        Comment insertedComment = em.find(Comment.class, comment.getId());

        assertThat(insertedComment).isNotNull().matches(c ->c.getContext().equals(COMMENT_NEW))
                .matches(c -> c.getBook() != null && c.getBook().getId()==FIRST_BOOK_ID);

    }

}
