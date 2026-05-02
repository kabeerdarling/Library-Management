package com.library.app.repository;

import com.library.app.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // Find author by email
    Optional<Author> findByEmail(String email);

    // Find authors by nationality
    List<Author> findByNationality(String nationality);

    // Custom query: find authors who have at least one book
    @Query("SELECT DISTINCT a FROM Author a INNER JOIN a.books b")
    List<Author> findAuthorsWithBooks();

    // Custom query: find authors by name (case insensitive)
    @Query("SELECT a FROM Author a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Author> findByNameContainingIgnoreCase(String name);
}
