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
public class LanguageController {

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
    @GetMapping("/languages")
    public String viewHomePage(Model model, Authentication auth) {
        User user = currentUserControllerAdvice
                .getCurrentUser(auth)
                .getUser();
        model.addAttribute("Role", user.getRole());

        List<Language> listLanguages = languageRepository.findAll();

        model.addAttribute("listLanguages", listLanguages);
        return "languages";
    }

    @GetMapping("/showNewLanguageForm")
    public String showNewEmployeeForm(Model model) {
        // create model attribute to bind form data
        Language language = new Language();
        List<Book> allBooks = bookRepository.findAll();

        model.addAttribute("language", language);
        model.addAttribute("allBooks", allBooks);
        return "new_language";
    }
    //
    @PostMapping("/saveLanguage")
    public String saveBook(@ModelAttribute("language") Language language) {
        languageRepository.save(language);
        List<Book> books = bookRepository.findBookByLanguage_Id(language.getId());
        books.forEach(book -> {
            if (!language.getBooks().contains(book)) {
                book.setLanguage(null);
                bookRepository.save(book);
            }
        });
        if (language.getBooks()!=null) {
            language.getBooks().forEach(book -> {
                if (book.getLanguage()==null) {
                    book.setLanguage(language);
                    bookRepository.save(book);
                }
                else if (book.getLanguage().equals(language)){
                    book.setLanguage(language);
                    bookRepository.save(book);
                }

            });
        }
        return "redirect:/languages";
    }
    //
    @GetMapping("/showFormForUpdateLanguage/{id}")
    public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
        Optional<Language> languageOptional = languageRepository.findById(id);
        Language language = languageOptional.get();

        List<Book> allBooks = bookRepository.findAll();

        model.addAttribute("language", language);
        model.addAttribute("allBooks", allBooks);
        return "update_language";
    }
    //
    @GetMapping("/deleteLanguage/{id}")
    public String deleteEmployee(@PathVariable (value = "id") long id) {
        Optional<Language> languageOptional = languageRepository.findById(id);
        Language language = languageOptional.get();
        language.getBooks().forEach(book -> {
            book.setLanguage(null);
            bookRepository.save(book);
        });

        languageRepository.deleteById(id);
        return "redirect:/languages";
    }

}
