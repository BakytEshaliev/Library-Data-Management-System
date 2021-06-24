package net.javaguides.springboot.controller;

import java.util.List;
import java.util.Optional;

import net.javaguides.springboot.model.*;
import net.javaguides.springboot.repository.*;
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
public class GenreController {

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
    @GetMapping("/genres")
    public String viewHomePage(Model model, Authentication auth) {
        User user = currentUserControllerAdvice
                .getCurrentUser(auth)
                .getUser();
        model.addAttribute("Role", user.getRole());

        List<Genre> listGenres = genreRepository.findAll();

        model.addAttribute("listGenres", listGenres);
        return "genres";
    }

    @GetMapping("/showNewGenreForm")
    public String showNewEmployeeForm(Model model) {
        // create model attribute to bind form data
        Genre genre = new Genre();
        List<Book> allBooks = bookRepository.findAll();

        model.addAttribute("genre", genre);
        model.addAttribute("allBooks", allBooks);
        return "new_genre";
    }
    //
    @PostMapping("/saveGenre")
    public String saveBook(@ModelAttribute("genre") Genre genre) {
        genreRepository.save(genre);
        List<Book> books = bookRepository.findBookByGenresContains(genre);
        books.forEach(book -> {
            if (!genre.getBooks().contains(book)) {
                book.removeGenre(genre.getId());
                bookRepository.save(book);
            }
        });
        genre.getBooks().forEach(book -> {
            if (!book.getGenres().contains(genre)) {
                book.addGenre(genre);
                bookRepository.save(book);
            }
        });
        return "redirect:/genres";
    }
    //
    @GetMapping("/showFormForUpdateGenre/{id}")
    public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {

        // get employee from the service
        Optional<Genre> genreOptional = genreRepository.findById(id);
        Genre genre = genreOptional.get();
        List<Book> allBooks = bookRepository.findAll();

        model.addAttribute("genre", genre);
        model.addAttribute("allBooks", allBooks);
        return "update_genre";
    }
    //
    @GetMapping("/deleteGenre/{id}")
    public String deleteEmployee(@PathVariable (value = "id") long id) {
        Optional<Genre> genreOptional = genreRepository.findById(id);
        Genre genre = genreOptional.get();
        genre.getBooks().forEach(book -> {
            book.removeGenre(genre.getId());
            bookRepository.save(book);
        });

        countryRepository.deleteById(id);
        return "redirect:/countries";
    }

}
