package com.library.app.service;

import com.library.app.entity.Author;
import com.library.app.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Get all authors.
     */
    @Transactional(readOnly = true)
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    /**
     * Get author by ID. Throws exception if not found.
     */
    @Transactional(readOnly = true)
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
    }

    /**
     * Create a new author. Handles duplicate email integrity violation.
     */
    public Author createAuthor(Author author) {
        try {
            return authorRepository.save(author);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                "An author with email '" + author.getEmail() + "' already exists.");
        }
    }

    /**
     * Update existing author.
     */
    public Author updateAuthor(Long id, Author updatedAuthor) {
        Author existing = getAuthorById(id);
        existing.setName(updatedAuthor.getName());
        existing.setEmail(updatedAuthor.getEmail());
        existing.setNationality(updatedAuthor.getNationality());
        existing.setBirthYear(updatedAuthor.getBirthYear());
        try {
            return authorRepository.save(existing);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                "Email '" + updatedAuthor.getEmail() + "' is already in use.");
        }
    }

    /**
     * Get authors who have at least one book (uses custom INNER JOIN query).
     */
    @Transactional(readOnly = true)
    public List<Author> getAuthorsWithBooks() {
        return authorRepository.findAuthorsWithBooks();
    }

    /**
     * Find author by email.
     */
    @Transactional(readOnly = true)
    public Optional<Author> findByEmail(String email) {
        return authorRepository.findByEmail(email);
    }
}
