package net.javaguides.springboot.controller;

import java.util.List;

import net.javaguides.springboot.model.*;
import net.javaguides.springboot.repository.AuthorRepository;
import net.javaguides.springboot.repository.GenreRepository;
import net.javaguides.springboot.repository.LanguageRepository;
import net.javaguides.springboot.repository.TypeRepository;
import net.javaguides.springboot.security.boundary.CurrentUserControllerAdvice;
import net.javaguides.springboot.security.domain.User;
import net.javaguides.springboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    CurrentUserControllerAdvice currentUserControllerAdvice;

    // display list of employees
    @GetMapping("/books")
    public String viewHomePage(Model model, Authentication auth) {
        User user = currentUserControllerAdvice
                .getCurrentUser(auth)
                .getUser();
        model.addAttribute("Role", user.getRole());

        List<Book> listBooks = bookService.getAllBooks();

        model.addAttribute("listBooks", listBooks);
        return "books";
    }

    @GetMapping("/showNewBookForm")
    public String showNewEmployeeForm(Model model) {
        // create model attribute to bind form data
        Book book = new Book();
        List<Author> allAuthors = authorRepository.findAll();
        List<Type> allTypes = typeRepository.findAll();
        List<Language> allLanguages = languageRepository.findAll();
        List<Genre> allGenres = genreRepository.findAll();

        model.addAttribute("allGenres", allGenres);
        model.addAttribute("allLanguages", allLanguages);
        model.addAttribute("allTypes", allTypes);
        model.addAttribute("allAuthors", allAuthors);
        model.addAttribute("book", book);
        return "new_book";
    }
//
    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute("book") Book book) {
        // save employee to database
        bookService.saveBook(book);
        return "redirect:/books";
    }
//
    @GetMapping("/showFormForUpdateBook/{id}")
    public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {

        // get employee from the service
        Book book = bookService.getBookById(id);
        List<Author> allAuthors = authorRepository.findAll();
        List<Type> allTypes = typeRepository.findAll();
        List<Language> allLanguages = languageRepository.findAll();
        List<Genre> allGenres = genreRepository.findAll();

        model.addAttribute("allGenres", allGenres);
        model.addAttribute("allLanguages", allLanguages);
        model.addAttribute("allTypes", allTypes);
        model.addAttribute("book", book);
        model.addAttribute("allAuthors", allAuthors);
        return "update_book";
    }
//
    @GetMapping("/deleteBook/{id}")
    public String deleteEmployee(@PathVariable (value = "id") long id) {

        // call delete employee method
        bookService.deleteBookById(id);
        return "redirect:/books";
    }

}
