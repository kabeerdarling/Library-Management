package com.library.app.repository;

import com.library.app.entity.Book;
import com.library.app.entity.BookAuthorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Find book by ISBN
    Optional<Book> findByIsbn(String isbn);

    // Find books by genre
    List<Book> findByGenre(String genre);

    // Find books by author id
    List<Book> findByAuthorId(Long authorId);

    /**
     * Custom JPQL INNER JOIN query between Book and Author tables.
     * Returns a list of BookAuthorDTO objects joining Book and Author data.
     */
    @Query("SELECT new com.library.app.entity.BookAuthorDTO(" +
           "b.id, b.title, b.genre, b.publishedYear, b.price, b.isbn, " +
           "a.id, a.name, a.email, a.nationality) " +
           "FROM Book b INNER JOIN b.author a")
    List<BookAuthorDTO> findAllBooksWithAuthors();

    /**
     * Inner join filtered by genre.
     */
    @Query("SELECT new com.library.app.entity.BookAuthorDTO(" +
           "b.id, b.title, b.genre, b.publishedYear, b.price, b.isbn, " +
           "a.id, a.name, a.email, a.nationality) " +
           "FROM Book b INNER JOIN b.author a WHERE b.genre = :genre")
    List<BookAuthorDTO> findBooksWithAuthorsByGenre(@Param("genre") String genre);

    // Find books by title (case insensitive)
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Book> findByTitleContainingIgnoreCase(@Param("title") String title);
}
