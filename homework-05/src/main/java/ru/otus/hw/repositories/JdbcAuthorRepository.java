package ru.otus.hw.repositories;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.Objects;
import java.util.HashMap;


@Repository
public class JdbcAuthorRepository implements AuthorRepository {

    private final JdbcOperations jdbc;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public JdbcAuthorRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = namedParameterJdbcOperations.getJdbcOperations();
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public List<Author> findAll() {
        return jdbc.query("select id, full_name from authors", new AuthorRowMapper());
    }


    @Override
    public Optional<Author> findById(long id) {
        Author author;
        try {
            Map<String, Object> params = Collections.singletonMap("id", id);
            author = namedParameterJdbcOperations.queryForObject(
                    "select id, full_name from authors where id = :id", params, new AuthorRowMapper()
            );
            return Optional.ofNullable(author);
        } catch (DataAccessException ex) {
            return Optional.empty();
        }
     }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from authors where id = :id", params
        );
    }

    @Override
    public Author insert(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("full_name", author.getFullName());
        KeyHolder kh = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update("insert into authors (full_name) values (:full_name)", params, kh);
        author.setId(Objects.requireNonNull(kh.getKey()).longValue());
        return author;
    }

    @Override
    public Author update(Author author) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", author.getId());
        params.put("fullName",author.getFullName());
        int count = namedParameterJdbcOperations.update(
                "update authors set full_name = :fullName where id = :id", params
        );
        if (count == 0) {
            throw new EntityNotFoundException("Could not update the book with id = %d".formatted(author.getId()));
        }
        return author;
    }

    private static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            long id = rs.getLong("id");
            String fullName = rs.getString("full_name");
            return new Author(id,fullName);
        }
    }
}
