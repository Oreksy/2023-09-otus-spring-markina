package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с авторами ")
@DataJpaTest
@Import({JpaAuthorRepository.class})
public class JpaAuthorRepositoryTest {

    @Autowired
    private JpaAuthorRepository repositoryJpa;

    @Autowired
    private TestEntityManager em;

    private static final long FIRST_AUTHOR_ID = 1L;

    private static final String AUTHOR_NAME = "Author_5000";

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 3;

    private static final int EXPECTED_QUERIES_COUNT = 1;



    @DisplayName("должен загружать информацию о нужном авторе по id")
    @Test
    void shouldReturnCorrectAuthorById() {
        var actualAuthor = repositoryJpa.findById(FIRST_AUTHOR_ID);
        var expectedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
                assertThat(actualAuthor).isPresent()
                .get()
                .usingRecursiveComparison().isEqualTo(expectedAuthor);
    }


    @DisplayName("должен загружать список всех авторов")
    @Test
    void shouldReturnCorrectAuthors() {
        val authors = repositoryJpa.findAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .allMatch(a -> !a.getFullName().isEmpty());
    }


    @DisplayName("должен удалять автора по id ")
    @Test
    void shouldDeleteAuthor() {
        val firstAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(firstAuthor).isNotNull();
        em.detach(firstAuthor);

        repositoryJpa.deleteById(FIRST_AUTHOR_ID);
        val deletedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);

        assertThat(deletedAuthor).isNull();
    }


    @DisplayName("должен сохранять измененного автора")
    @Test
    void shouldSaveUpdatedAuthor() {
        val firstAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        String oldName = firstAuthor.getFullName();
        em.detach(firstAuthor);

        Author newAuthor = new Author(FIRST_AUTHOR_ID,AUTHOR_NAME);
        repositoryJpa.save(newAuthor);

        val updateAuthor = em.find(Author.class, FIRST_AUTHOR_ID);

        assertThat(updateAuthor.getFullName()).isNotEqualTo(oldName).isEqualTo(AUTHOR_NAME);
    }


    @DisplayName("должен сохранять нового автора")
    @Test
    void shouldSaveNewAuthor() {
        val author = new Author(0, AUTHOR_NAME);
        val newAuthor = repositoryJpa.save(author);
        assertThat(newAuthor.getId()).isGreaterThan(0);

        val actualAuthor = em.find(Author.class, newAuthor.getId());
        assertThat(actualAuthor).isNotNull().matches(a -> !a.getFullName().equals(""));

    }
}
