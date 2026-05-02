package com.library.app.service;

import com.library.app.entity.Author;
import com.library.app.entity.Book;
import com.library.app.entity.BookAuthorDTO;
import com.library.app.repository.AuthorRepository;
import com.library.app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    /**
     * Get all books.
     */
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Get book by ID.
     */
    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    /**
     * Create a new book. Validates the author exists and handles integrity violations.
     */
    public Book createBook(Book book, Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + authorId));
        book.setAuthor(author);
        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                "A book with ISBN '" + book.getIsbn() + "' already exists.");
        }
    }

    /**
     * Update existing book details.
     */
    public Book updateBook(Long id, Book updatedBook, Long authorId) {
        Book existing = getBookById(id);
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + authorId));

        existing.setTitle(updatedBook.getTitle());
        existing.setGenre(updatedBook.getGenre());
        existing.setPublishedYear(updatedBook.getPublishedYear());
        existing.setPrice(updatedBook.getPrice());
        existing.setIsbn(updatedBook.getIsbn());
        existing.setAuthor(author);

        try {
            return bookRepository.save(existing);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                "ISBN '" + updatedBook.getIsbn() + "' is already in use.");
        }
    }

    /**
     * Get all books joined with their author details (INNER JOIN query).
     */
    @Transactional(readOnly = true)
    public List<BookAuthorDTO> getAllBooksWithAuthors() {
        return bookRepository.findAllBooksWithAuthors();
    }

    /**
     * Get books by genre.
     */
    @Transactional(readOnly = true)
    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }

    /**
     * Get books by author ID.
     */
    @Transactional(readOnly = true)
    public List<Book> getBooksByAuthor(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }
}
