package ru.otus.hw.repositories;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;

import java.util.Optional;
import java.util.List;

@Repository
public class JpaAuthorRepository implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    public JpaAuthorRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
     }

    @Override
    public void deleteById(long id) {
        Optional<Author> findAuthor = Optional.ofNullable(em.find(Author.class, id));
        findAuthor.ifPresent(em::remove);
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == 0) {
            em.persist(author);
            return author;
        }
        return em.merge(author);
    }
}
