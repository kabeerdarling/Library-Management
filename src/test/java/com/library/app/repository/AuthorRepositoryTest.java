package com.library.app.repository;

import com.library.app.entity.Author;
import com.library.app.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    private Author author1;
    private Author author2;

    @BeforeEach
    void setUp() {
        author1 = authorRepository.save(
            new Author("George Orwell", "gorwell@mail.com", "British", 1903));
        author2 = authorRepository.save(
            new Author("J.K. Rowling", "jkrowling@mail.com", "British", 1965));
        authorRepository.save(
            new Author("Cien Anos", "cien@mail.com", "Colombian", 1927));

        // Give author1 a book (for inner-join test)
        bookRepository.save(new Book("1984", "Dystopian", 1949, 12.99, "ISBN-A01", author1));
    }

    @Test
    void testFindAll_ReturnsAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        assertEquals(3, authors.size());
    }

    @Test
    void testFindByEmail_WhenExists_ReturnsAuthor() {
        Optional<Author> found = authorRepository.findByEmail("gorwell@mail.com");
        assertTrue(found.isPresent());
        assertEquals("George Orwell", found.get().getName());
    }

    @Test
    void testFindByEmail_WhenNotExists_ReturnsEmpty() {
        Optional<Author> found = authorRepository.findByEmail("nobody@mail.com");
        assertFalse(found.isPresent());
    }

    @Test
    void testFindByNationality_ReturnsCorrectAuthors() {
        List<Author> british = authorRepository.findByNationality("British");
        assertEquals(2, british.size());

        List<Author> colombian = authorRepository.findByNationality("Colombian");
        assertEquals(1, colombian.size());
    }

    @Test
    void testFindAuthorsWithBooks_ReturnsOnlyAuthorsWithBooks() {
        List<Author> withBooks = authorRepository.findAuthorsWithBooks();
        // Only author1 has a book
        assertEquals(1, withBooks.size());
        assertEquals("George Orwell", withBooks.get(0).getName());
    }

    @Test
    void testSaveAuthor_PersistsCorrectly() {
        Author newAuthor = new Author("New Author", "new@mail.com", "French", 1980);
        Author saved = authorRepository.save(newAuthor);

        assertNotNull(saved.getId());
        assertEquals("New Author", saved.getName());
        assertEquals("French", saved.getNationality());
    }

    @Test
    void testUpdateAuthor_ChangesArePersisted() {
        author1.setName("George Orwell Updated");
        authorRepository.save(author1);

        Author updated = authorRepository.findById(author1.getId()).orElseThrow();
        assertEquals("George Orwell Updated", updated.getName());
    }

    @Test
    void testFindById_WhenExists_ReturnsAuthor() {
        Optional<Author> found = authorRepository.findById(author1.getId());
        assertTrue(found.isPresent());
        assertEquals("George Orwell", found.get().getName());
    }

    @Test
    void testFindById_WhenNotExists_ReturnsEmpty() {
        Optional<Author> found = authorRepository.findById(999L);
        assertFalse(found.isPresent());
    }
}
