package net.library.springboot.controller;

import java.util.List;
import java.util.Optional;

import net.library.springboot.repository.*;
import net.library.springboot.security.boundary.CurrentUserControllerAdvice;
import net.library.springboot.security.domain.User;
import net.library.springboot.service.BookService;
import net.library.springboot.model.Author;
import net.library.springboot.model.Book;
import net.library.springboot.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthorController {

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
    private CountryRepository countryRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    CurrentUserControllerAdvice currentUserControllerAdvice;

    // display list of employees
    @GetMapping("/authors")
    public String viewHomePage(Model model, Authentication auth) {
        User user = currentUserControllerAdvice
                .getCurrentUser(auth)
                .getUser();
        model.addAttribute("Role", user.getRole());

        List<Author> listAuthors = authorRepository.findAll();


        model.addAttribute("listAuthors", listAuthors);
        return "authors";
    }

    @GetMapping("/showNewAuthorForm")
    public String showNewEmployeeForm(Model model) {
        // create model attribute to bind form data
        Author author = new Author();
        List<Country> allCounties = countryRepository.findAll();
        List<Book> allBooks = bookRepository.findAll();

        model.addAttribute("author", author);
        model.addAttribute("allCounties", allCounties);
        model.addAttribute("allBooks", allBooks);
        return "new_author";
    }
    //
    @PostMapping("/saveAuthor")
    public String saveBook(@ModelAttribute("author") Author author) {
        authorRepository.save(author);
        List<Book> books = bookRepository.findBookByAuthorsContains(author);
        books.forEach(book -> {
            if (!author.getBooks().contains(book)) {
                book.removeAuthor(author.getId());
                bookRepository.save(book);
            }
        });
        author.getBooks().forEach(book -> {
            if (!book.getAuthors().contains(author)) {
                book.addAuthor(author);
                bookRepository.save(book);
            }
        });
        return "redirect:/authors";
    }
    //
    @GetMapping("/showFormForUpdateAuthor/{id}")
    public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {

        // get employee from the service
        Optional<Author> authorOptional = authorRepository.findById(id);
        Author author = authorOptional.get();
        List<Country> allCounties = countryRepository.findAll();
        List<Book> allBooks = bookRepository.findAll();

        model.addAttribute("author", author);
        model.addAttribute("allCounties", allCounties);
        model.addAttribute("allBooks", allBooks);
        return "update_author";
    }
    //
    @GetMapping("/deleteAuthor/{id}")
    public String deleteEmployee(@PathVariable (value = "id") long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        Author author = authorOptional.get();
        author.getBooks().forEach(book -> {
            book.getAuthors().remove(author);
            bookRepository.save(book);
        });
        authorRepository.deleteById(id);
        return "redirect:/authors";
    }

}
