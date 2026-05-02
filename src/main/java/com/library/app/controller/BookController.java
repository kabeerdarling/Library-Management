package com.library.app.controller;

import com.library.app.entity.Book;
import com.library.app.service.AuthorService;
import com.library.app.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    /**
     * READ: List all books (with author info via INNER JOIN).
     */
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("booksWithAuthors", bookService.getAllBooksWithAuthors());
        model.addAttribute("pageTitle", "All Books");
        return "books/list";
    }

    /**
     * CREATE: Show form to add a new book.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("pageTitle", "Add New Book");
        return "books/form";
    }

    /**
     * CREATE: Handle form submission to save a new book.
     */
    @PostMapping("/save")
    public String saveBook(@Valid @ModelAttribute("book") Book book,
                           BindingResult result,
                           @RequestParam("authorId") Long authorId,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("pageTitle", "Add New Book");
            return "books/form";
        }
        try {
            bookService.createBook(book, authorId);
            redirectAttributes.addFlashAttribute("successMessage",
                "Book '" + book.getTitle() + "' created successfully!");
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("pageTitle", "Add New Book");
            return "books/form";
        }
        return "redirect:/books";
    }

    /**
     * UPDATE: Show form to edit an existing book.
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("selectedAuthorId", book.getAuthor().getId());
        model.addAttribute("pageTitle", "Edit Book");
        return "books/form";
    }

    /**
     * UPDATE: Handle form submission to update an existing book.
     */
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id,
                             @Valid @ModelAttribute("book") Book book,
                             BindingResult result,
                             @RequestParam("authorId") Long authorId,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("pageTitle", "Edit Book");
            return "books/form";
        }
        try {
            bookService.updateBook(id, book, authorId);
            redirectAttributes.addFlashAttribute("successMessage",
                "Book updated successfully!");
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("pageTitle", "Edit Book");
            return "books/form";
        }
        return "redirect:/books";
    }
}
