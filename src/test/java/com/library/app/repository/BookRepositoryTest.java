package com.library.app.repository;

import com.library.app.entity.Author;
import com.library.app.entity.Book;
import com.library.app.entity.BookAuthorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private Author savedAuthor;

    @BeforeEach
    void setUp() {
        savedAuthor = authorRepository.save(
            new Author("Test Author", "testauthor@mail.com", "American", 1970));

        bookRepository.save(new Book("Test Book 1", "Drama",   2000, 10.0, "ISBN-T01", savedAuthor));
        bookRepository.save(new Book("Test Book 2", "Drama",   2010, 11.0, "ISBN-T02", savedAuthor));
        bookRepository.save(new Book("Test Book 3", "Fantasy", 2020, 12.0, "ISBN-T03", savedAuthor));
    }

    @Test
    void testFindAll_ReturnsAllBooks() {
        List<Book> books = bookRepository.findAll();
        assertEquals(3, books.size());
    }

    @Test
    void testFindByIsbn_WhenExists_ReturnsBook() {
        Optional<Book> book = bookRepository.findByIsbn("ISBN-T01");
        assertTrue(book.isPresent());
        assertEquals("Test Book 1", book.get().getTitle());
    }

    @Test
    void testFindByIsbn_WhenNotExists_ReturnsEmpty() {
        Optional<Book> book = bookRepository.findByIsbn("ISBN-FAKE");
        assertFalse(book.isPresent());
    }

    @Test
    void testFindByGenre_ReturnsCorrectBooks() {
        List<Book> dramaBooks = bookRepository.findByGenre("Drama");
        assertEquals(2, dramaBooks.size());

        List<Book> fantasyBooks = bookRepository.findByGenre("Fantasy");
        assertEquals(1, fantasyBooks.size());
        assertEquals("Test Book 3", fantasyBooks.get(0).getTitle());
    }

    @Test
    void testFindByAuthorId_ReturnsCorrectBooks() {
        List<Book> books = bookRepository.findByAuthorId(savedAuthor.getId());
        assertEquals(3, books.size());
    }

    @Test
    void testFindAllBooksWithAuthors_InnerJoin_ReturnsDTO() {
        List<BookAuthorDTO> dtos = bookRepository.findAllBooksWithAuthors();

        assertEquals(3, dtos.size());
        // Verify join data is present
        dtos.forEach(dto -> {
            assertNotNull(dto.getBookTitle());
            assertNotNull(dto.getAuthorName());
            assertEquals("Test Author", dto.getAuthorName());
            assertEquals("testauthor@mail.com", dto.getAuthorEmail());
        });
    }

    @Test
    void testFindBooksWithAuthorsByGenre_FiltersCorrectly() {
        List<BookAuthorDTO> dtos = bookRepository.findBooksWithAuthorsByGenre("Drama");

        assertEquals(2, dtos.size());
        dtos.forEach(dto -> assertEquals("Drama", dto.getGenre()));
    }

    @Test
    void testSaveBook_PersistsCorrectly() {
        Book newBook = new Book("New Book", "Sci-Fi", 2023, 15.0, "ISBN-NEW", savedAuthor);
        Book saved = bookRepository.save(newBook);

        assertNotNull(saved.getId());
        assertEquals("New Book", saved.getTitle());
        assertEquals(savedAuthor.getId(), saved.getAuthor().getId());
    }

    @Test
    void testFindById_WhenExists_ReturnsBook() {
        Book first = bookRepository.findAll().get(0);
        Optional<Book> found = bookRepository.findById(first.getId());

        assertTrue(found.isPresent());
        assertEquals(first.getTitle(), found.get().getTitle());
    }
}
