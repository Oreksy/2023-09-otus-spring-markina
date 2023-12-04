package ru.otus.hw.repositories;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.HashMap;


@Repository
public class JdbcBookRepository implements BookRepository {

    private final JdbcOperations jdbc;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public JdbcBookRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = namedParameterJdbcOperations.getJdbcOperations();
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Optional<Book> findById(long id) {
        Book book;
        try {
            Map<String, Object> params = Collections.singletonMap("id", id);
            book = namedParameterJdbcOperations.queryForObject(
               "select books.id, books.title, authors.id, authors.full_name, genres.id, genres.name from books " +
                       "left join authors on books.author_id=authors.id " +
                       "left join genres on books.genre_id=genres.id where books.id = :id", params, new BookRowMapper()
            );
            return Optional.ofNullable(book);
        } catch (DataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query("select books.id, books.title, authors.id, authors.full_name, genres.id, genres.name " +
                "from books left join authors on books.author_id=authors.id " +
                "left join genres on books.genre_id=genres.id", new BookRowMapper());
    }

    @Override
    public List<Book> findAllByAuthorId(long authorId) {
        Map<String, Object> params = Collections.singletonMap("id", authorId);
        return  namedParameterJdbcOperations.query(
              "select books.id, books.title, authors.id, authors.full_name, genres.id, genres.name from books " +
                     "left join authors on books.author_id=authors.id " +
                     "left join genres on books.genre_id=genres.id where authors.id = :id", params, new BookRowMapper()
        );
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from books where id = :id", params
        );
    }

    private Book insert(Book book) {
       var keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("authorId", book.getAuthor().getId());
        params.addValue("genreId", book.getGenre().getId());

        namedParameterJdbcOperations.update("insert into books (title,author_id,genre_id) " +
                "values (:title,:authorId,:genreId)", params, keyHolder);

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {

        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("title", book.getTitle());
        params.put("authorId", book.getAuthor().getId());
        params.put("genreId", book.getGenre().getId());
        int count = namedParameterJdbcOperations.update(
                "update books set title=:title, author_id=:authorId, genre_id=:genreId where id = :id", params
        );

        // Выбросить EntityNotFoundException если не обновлено ни одной записи в БД
        if (count == 0) {
            throw new EntityNotFoundException("Could not update the book with id = %d".formatted(book.getId()));
        }
        return book;
    }

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long idBook = rs.getLong("books.id");
            String title = rs.getString("books.title");
            long idAuthor = rs.getLong("authors.id");
            String fullName = rs.getString("authors.full_name");
            long idGenre = rs.getLong("genres.id");
            String name = rs.getString("genres.name");
            return new Book(idBook,title,new Author(idAuthor,fullName),new Genre(idGenre,name));
        }
    }
}
