package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Book;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Репозиторий на основе Mongo для работы с книгами ")
@DataMongoTest
public class BookRepositoryTest {
    private static final String FIRST_BOOK_ID = "1";

    private static final int EXPECTED_NUMBER_OF_BOOKS = 3;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName("должен загружать информацию о нужной книге по id")
    @Test
    void shouldReturnCorrectBookById() {

        Book expectedBook = mongoTemplate.findById(FIRST_BOOK_ID,Book.class);

        Optional<Book> actualBook = bookRepository.findById(FIRST_BOOK_ID);
         assertThat(actualBook).isPresent()
                .get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {

        val books = bookRepository.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(a -> !a.getTitle().isEmpty())
                .allMatch(a -> a.getAuthor() != null)
                .allMatch(a -> a.getGenre()!= null);
    }

    @DisplayName("должен загружать книги по id автора")
    @Test
    void shouldReturnCorrectBooksByAuthorId() {

        Book expectedBook = mongoTemplate.findById(FIRST_BOOK_ID,Book.class);

        val books = bookRepository.findAllByAuthorId(expectedBook.getAuthor().getId());

        assertThat(books.get(0)).isNotNull().matches(a -> a.getTitle().equals(expectedBook.getTitle()))
                .matches(a-> a.getId().equals(expectedBook.getId()))
                .matches(a-> a.getAuthor().getFullName().equals(expectedBook.getAuthor().getFullName()));

    }

}
