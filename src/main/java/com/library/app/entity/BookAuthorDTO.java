package com.library.app.entity;

/**
 * DTO to hold the result of the INNER JOIN query between Book and Author.
 */
public class BookAuthorDTO {

    private Long bookId;
    private String bookTitle;
    private String genre;
    private Integer publishedYear;
    private Double price;
    private String isbn;
    private Long authorId;
    private String authorName;
    private String authorEmail;
    private String nationality;

    // Constructor used by JPQL constructor expression
    public BookAuthorDTO(Long bookId, String bookTitle, String genre,
                         Integer publishedYear, Double price, String isbn,
                         Long authorId, String authorName, String authorEmail,
                         String nationality) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.genre = genre;
        this.publishedYear = publishedYear;
        this.price = price;
        this.isbn = isbn;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.nationality = nationality;
    }

    // Getters and Setters
    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Integer getPublishedYear() { return publishedYear; }
    public void setPublishedYear(Integer publishedYear) { this.publishedYear = publishedYear; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getAuthorEmail() { return authorEmail; }
    public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
}
