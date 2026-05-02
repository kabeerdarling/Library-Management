package com.library.app.service;

import com.library.app.entity.Author;
import com.library.app.entity.Book;
import com.library.app.entity.BookAuthorDTO;
import com.library.app.repository.AuthorRepository;
import com.library.app.repository.BookRepository;
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
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;

    private Author author;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        author = new Author("George Orwell", "gorwell@mail.com", "British", 1903);
        author.setId(1L);

        book1 = new Book("1984", "Dystopian", 1949, 12.99, "ISBN-001", author);
        book1.setId(1L);

        book2 = new Book("Animal Farm", "Political", 1945, 9.99, "ISBN-002", author);
        book2.setId(2L);
    }

    @Test
    void testGetAllBooks_ReturnsListOfBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        assertEquals("1984", result.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById_WhenExists_ReturnsBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        Book result = bookService.getBookById(1L);

        assertNotNull(result);
        assertEquals("1984", result.getTitle());
        assertEquals("Dystopian", result.getGenre());
    }

    @Test
    void testGetBookById_WhenNotExists_ThrowsException() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> bookService.getBookById(99L));

        assertTrue(ex.getMessage().contains("Book not found with id: 99"));
    }

    @Test
    void testCreateBook_Success() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenReturn(book1);

        Book result = bookService.createBook(book1, 1L);

        assertNotNull(result);
        assertEquals("1984", result.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testCreateBook_AuthorNotFound_ThrowsException() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> bookService.createBook(book1, 99L));

        assertTrue(ex.getMessage().contains("Author not found with id: 99"));
    }

    @Test
    void testCreateBook_DuplicateIsbn_ThrowsException() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate ISBN"));

        assertThrows(DataIntegrityViolationException.class,
                () -> bookService.createBook(book1, 1L));
    }

    @Test
    void testUpdateBook_Success() {
        Book updatedBook = new Book("1984 - Revised", "Dystopian", 1949, 14.99, "ISBN-001", author);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenReturn(book1);

        Book result = bookService.updateBook(1L, updatedBook, 1L);

        assertNotNull(result);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testGetAllBooksWithAuthors_CallsInnerJoinQuery() {
        BookAuthorDTO dto = new BookAuthorDTO(
                1L, "1984", "Dystopian", 1949, 12.99, "ISBN-001",
                1L, "George Orwell", "gorwell@mail.com", "British");
        when(bookRepository.findAllBooksWithAuthors()).thenReturn(List.of(dto));

        List<BookAuthorDTO> result = bookService.getAllBooksWithAuthors();

        assertEquals(1, result.size());
        assertEquals("1984", result.get(0).getBookTitle());
        assertEquals("George Orwell", result.get(0).getAuthorName());
        verify(bookRepository, times(1)).findAllBooksWithAuthors();
    }

    @Test
    void testGetBooksByGenre_ReturnsFilteredBooks() {
        when(bookRepository.findByGenre("Dystopian")).thenReturn(List.of(book1));

        List<Book> result = bookService.getBooksByGenre("Dystopian");

        assertEquals(1, result.size());
        assertEquals("Dystopian", result.get(0).getGenre());
    }

    @Test
    void testGetBooksByAuthor_ReturnsAuthorBooks() {
        when(bookRepository.findByAuthorId(1L)).thenReturn(Arrays.asList(book1, book2));

        List<Book> result = bookService.getBooksByAuthor(1L);

        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findByAuthorId(1L);
    }
}
