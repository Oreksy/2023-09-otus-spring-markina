package ru.otus.hw.repositories;

import jakarta.persistence.TypedQuery;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
@DataJpaTest
@Import({JpaBookRepository.class})
public class JpaBookRepositoryTest {

    @Autowired
    private JpaBookRepository repositoryJpa;

    @Autowired
    private TestEntityManager em;

    private static final long FIRST_BOOK_ID = 1L;

    private static final long FIRST_AUTHOR_ID = 1L;

    private static final long FIRST_GENRE_ID = 1L;

    private static final String BOOK_NAME = "Book_5000";

    private static final int EXPECTED_NUMBER_OF_BOOKS = 3;

    private List<Author> authors;

    private List<Genre> genres;

    @BeforeEach
    void setUp() {
        authors = getAllAuthors();
        genres = getAllGenres();
    }

    @DisplayName("должен загружать информацию о нужной книге по id")
    @Test
    void shouldReturnCorrectBookById() {
        var actualBook = repositoryJpa.findById(FIRST_BOOK_ID);
        var expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(actualBook).isPresent()
                .get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {

        val books = repositoryJpa.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(a -> !a.getTitle().isEmpty())
                .allMatch(a -> a.getAuthor() != null)
                .allMatch(a -> a.getGenre()!= null);
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        val firstBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(firstBook).isNotNull();
        em.detach(firstBook);

        repositoryJpa.deleteById(FIRST_BOOK_ID);
        val deletedBook = em.find(Book.class, FIRST_BOOK_ID);

        assertThat(deletedBook).isNull();
    }

    @DisplayName("должен сохранять измененное название книги")
    @Test
    void shouldSaveUpdatedBook() {
        val firstBook = em.find(Book.class, FIRST_BOOK_ID);
        String oldTitle = firstBook.getTitle();
        em.detach(firstBook);

        Author author = getAllAuthors().stream().filter(a -> a.getId()==firstBook.getAuthor().getId()).findFirst().get();
        Genre genre = getAllGenres().stream().filter(g ->g.getId()==firstBook.getGenre().getId()).findFirst().get();

        Book bookNewTitle = new Book(firstBook.getId(), BOOK_NAME, author, genre);

        Book updateBook = repositoryJpa.save(bookNewTitle);

        assertThat(updateBook.getTitle()).isNotEqualTo(oldTitle).isEqualTo(BOOK_NAME);
    }

    @DisplayName("должен корректно сохранять все поля книги")
    @Test
    void shouldSaveInsertedBook() {

        Author author = getAllAuthors().stream().filter(a -> a.getId()==FIRST_AUTHOR_ID).findFirst().get();
        Genre genre = getAllGenres().stream().filter(g ->g.getId()==FIRST_GENRE_ID).findFirst().get();

        Book book = new Book(0, BOOK_NAME, author, genre);

        repositoryJpa.save(book);
        assertThat(book.getId()).isGreaterThan(0);

        assertThat(book).isNotNull().matches(b ->b.getTitle().equals(BOOK_NAME))
                .matches(b -> b.getAuthor() != null && b.getAuthor().getId()==FIRST_AUTHOR_ID)
                .matches(b -> b.getGenre() != null && b.getGenre().getId()==FIRST_GENRE_ID);

    }

    private List<Author> getAllAuthors() {
        TypedQuery<Author> query = em.getEntityManager()
                .createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    private List<Genre> getAllGenres() {
        TypedQuery<Genre> query = em.getEntityManager()
                .createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }
}
