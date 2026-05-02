package com.library.app;

import com.library.app.entity.Author;
import com.library.app.entity.Book;
import com.library.app.repository.AuthorRepository;
import com.library.app.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner populateData(AuthorRepository authorRepo,
                                          BookRepository bookRepo) {
        return args -> {
            // Create 10 Authors
            Author a1 = authorRepo.save(new Author("George Orwell",     "gorwell@mail.com",    "British",   1903));
            Author a2 = authorRepo.save(new Author("J.K. Rowling",      "jkrowling@mail.com",  "British",   1965));
            Author a3 = authorRepo.save(new Author("F. Scott Fitzgerald","fitzgerald@mail.com", "American",  1896));
            Author a4 = authorRepo.save(new Author("Harper Lee",        "hlee@mail.com",       "American",  1926));
            Author a5 = authorRepo.save(new Author("Gabriel Marquez",   "gmarquez@mail.com",   "Colombian", 1927));
            Author a6 = authorRepo.save(new Author("Toni Morrison",     "tmorrison@mail.com",  "American",  1931));
            Author a7 = authorRepo.save(new Author("Leo Tolstoy",       "ltolstoy@mail.com",   "Russian",   1828));
            Author a8 = authorRepo.save(new Author("Jane Austen",       "jausten@mail.com",    "British",   1775));
            Author a9 = authorRepo.save(new Author("Mark Twain",        "mtwain@mail.com",     "American",  1835));
            Author a10 = authorRepo.save(new Author("Ernest Hemingway", "ehemingway@mail.com", "American",  1899));

            // Create 10 Books (one per author for neat data)
            bookRepo.save(new Book("1984",                      "Dystopian",   1949, 12.99, "ISBN-001", a1));
            bookRepo.save(new Book("Harry Potter & Sorcerer",   "Fantasy",     1997, 14.99, "ISBN-002", a2));
            bookRepo.save(new Book("The Great Gatsby",          "Classic",     1925, 10.99, "ISBN-003", a3));
            bookRepo.save(new Book("To Kill a Mockingbird",     "Drama",       1960, 11.99, "ISBN-004", a4));
            bookRepo.save(new Book("100 Years of Solitude",     "Magical",     1967, 13.99, "ISBN-005", a5));
            bookRepo.save(new Book("Beloved",                   "Historical",  1987, 12.49, "ISBN-006", a6));
            bookRepo.save(new Book("War and Peace",             "Historical",  1869, 15.99, "ISBN-007", a7));
            bookRepo.save(new Book("Pride and Prejudice",       "Romance",     1813,  9.99, "ISBN-008", a8));
            bookRepo.save(new Book("Adventures of Huckleberry", "Adventure",   1884,  8.99, "ISBN-009", a9));
            bookRepo.save(new Book("The Old Man and the Sea",   "Drama",       1952, 10.49, "ISBN-010", a10));

            System.out.println("✅ Database populated with 10 Authors and 10 Books.");
        };
    }
}
