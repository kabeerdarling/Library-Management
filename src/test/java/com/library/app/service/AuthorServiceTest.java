package com.library.app.service;

import com.library.app.entity.Author;
import com.library.app.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author1;
    private Author author2;

    @BeforeEach
    void setUp() {
        author1 = new Author("George Orwell", "gorwell@mail.com", "British", 1903);
        author1.setId(1L);

        author2 = new Author("J.K. Rowling", "jkrowling@mail.com", "British", 1965);
        author2.setId(2L);
    }

    @Test
    void testGetAllAuthors_ReturnsListOfAuthors() {
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));

        List<Author> result = authorService.getAllAuthors();

        assertEquals(2, result.size());
        assertEquals("George Orwell", result.get(0).getName());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testGetAuthorById_WhenExists_ReturnsAuthor() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));

        Author result = authorService.getAuthorById(1L);

        assertNotNull(result);
        assertEquals("George Orwell", result.getName());
        assertEquals("gorwell@mail.com", result.getEmail());
    }

    @Test
    void testGetAuthorById_WhenNotExists_ThrowsException() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> authorService.getAuthorById(99L));

        assertTrue(ex.getMessage().contains("Author not found with id: 99"));
    }

    @Test
    void testCreateAuthor_Success() {
        when(authorRepository.save(any(Author.class))).thenReturn(author1);

        Author result = authorService.createAuthor(author1);

        assertNotNull(result);
        assertEquals("George Orwell", result.getName());
        verify(authorRepository, times(1)).save(author1);
    }

    @Test
    void testCreateAuthor_DuplicateEmail_ThrowsException() {
        when(authorRepository.save(any(Author.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate"));

        assertThrows(DataIntegrityViolationException.class,
                () -> authorService.createAuthor(author1));
    }

    @Test
    void testUpdateAuthor_Success() {
        Author updated = new Author("George Orwell Updated", "gorwell_new@mail.com", "British", 1903);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));
        when(authorRepository.save(any(Author.class))).thenReturn(author1);

        Author result = authorService.updateAuthor(1L, updated);

        assertNotNull(result);
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void testGetAuthorsWithBooks_CallsCustomQuery() {
        when(authorRepository.findAuthorsWithBooks()).thenReturn(List.of(author1));

        List<Author> result = authorService.getAuthorsWithBooks();

        assertEquals(1, result.size());
        verify(authorRepository, times(1)).findAuthorsWithBooks();
    }

    @Test
    void testFindByEmail_WhenExists_ReturnsAuthor() {
        when(authorRepository.findByEmail("gorwell@mail.com")).thenReturn(Optional.of(author1));

        Optional<Author> result = authorService.findByEmail("gorwell@mail.com");

        assertTrue(result.isPresent());
        assertEquals("George Orwell", result.get().getName());
    }

    @Test
    void testFindByEmail_WhenNotExists_ReturnsEmpty() {
        when(authorRepository.findByEmail("nobody@mail.com")).thenReturn(Optional.empty());

        Optional<Author> result = authorService.findByEmail("nobody@mail.com");

        assertFalse(result.isPresent());
    }
}
