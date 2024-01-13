package ru.otus.hw.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    private List<Author> authors;

    private List<Book> books;

    private List<Genre> genres;

    @ChangeSet(order = "001", id = "dropDb", author = "markina", runAlways = true)
    public void dropDb(MongoDatabase myMongo) {
        myMongo.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "markina")
    public void insertAuthors(AuthorRepository repository) {
        authors = new ArrayList<>();
        authors.add(repository.save(new Author("Александр Сергеевич Пушкин")));
        authors.add(repository.save(new Author("Лев Николаевич Толстой")));
        authors.add(repository.save(new Author("Виктор Олегович Пелевин")));
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "markina")
    public void insertGenres(GenreRepository repository) {
        genres = new ArrayList<>();
        genres.add(repository.save(new Genre("рассказ")));
        genres.add(repository.save(new Genre("роман")));
        genres.add(repository.save(new Genre("сатира")));

    }

    @ChangeSet(order = "004", id = "insertBooks", author = "markina")
    public void insertBooks(BookRepository repository) {
        books = new ArrayList<>();
        books.add(repository.save(new Book("Метель",authors.get(0), genres.get(0))));
        books.add(repository.save(new Book("Война и мир",authors.get(1), genres.get(1))));
        books.add(repository.save(
                new Book("Чапаев и Пустота",authors.get(2), genres.get(2))));
    }

    @ChangeSet(order = "005", id = "insertComments", author = "markina")
    public void insertComments(CommentRepository repository) {
        repository.save(new Comment("Хороший рассказ", books.get(0)));
        repository.save(new Comment("Скучно", books.get(1)));
        repository.save(new Comment("Интересно", books.get(2)));
    }

}
