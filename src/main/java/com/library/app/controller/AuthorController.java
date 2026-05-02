package com.library.app.controller;

import com.library.app.entity.Author;
import com.library.app.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * READ: List all authors.
     */
    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("pageTitle", "All Authors");
        return "authors/list";
    }

    /**
     * READ: Show authors with their books (inner join result).
     */
    @GetMapping("/with-books")
    public String authorsWithBooks(Model model) {
        model.addAttribute("authors", authorService.getAuthorsWithBooks());
        model.addAttribute("pageTitle", "Authors With Books");
        return "authors/with-books";
    }

    /**
     * CREATE: Show form to add a new author.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("author", new Author());
        model.addAttribute("pageTitle", "Add New Author");
        return "authors/form";
    }

    /**
     * CREATE: Handle form submission to save a new author.
     */
    @PostMapping("/save")
    public String saveAuthor(@Valid @ModelAttribute("author") Author author,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Add New Author");
            return "authors/form";
        }
        try {
            authorService.createAuthor(author);
            redirectAttributes.addFlashAttribute("successMessage",
                "Author '" + author.getName() + "' created successfully!");
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("pageTitle", "Add New Author");
            return "authors/form";
        }
        return "redirect:/authors";
    }

    /**
     * UPDATE: Show form to edit an existing author.
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        model.addAttribute("pageTitle", "Edit Author");
        return "authors/form";
    }

    /**
     * UPDATE: Handle form submission to update an existing author.
     */
    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable Long id,
                               @Valid @ModelAttribute("author") Author author,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Author");
            return "authors/form";
        }
        try {
            authorService.updateAuthor(id, author);
            redirectAttributes.addFlashAttribute("successMessage",
                "Author updated successfully!");
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("pageTitle", "Edit Author");
            return "authors/form";
        }
        return "redirect:/authors";
    }
}
