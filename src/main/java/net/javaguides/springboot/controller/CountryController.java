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
public class CountryController {

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
    @GetMapping("/countries")
    public String viewHomePage(Model model, Authentication auth) {
        User user = currentUserControllerAdvice
                .getCurrentUser(auth)
                .getUser();
        model.addAttribute("Role", user.getRole());

        List<Country> listCountries = countryRepository.findAll();

        model.addAttribute("listCountries", listCountries);
        return "countries";
    }

    @GetMapping("/showNewCountryForm")
    public String showNewEmployeeForm(Model model) {
        // create model attribute to bind form data
        Country country = new Country();
        List<Author> allAuthors = authorRepository.findAll();

        model.addAttribute("country", country);
        model.addAttribute("allAuthors", allAuthors);
        return "new_country";
    }
    //
    @PostMapping("/saveCountry")
    public String saveBook(@ModelAttribute("country") Country country) {
        countryRepository.save(country);
        List<Author> authors = authorRepository.findAuthorByCountry_Id(country.getId());
        authors.forEach(author -> {
            if (!country.getAuthors().contains(author)) {
                author.setCountry(null);
                authorRepository.save(author);
            }
        });
        if (country.getAuthors()!=null) {
            country.getAuthors().forEach(author -> {
                    if (author.getCountry()==null) {
                        author.setCountry(country);
                        authorRepository.save(author);
                    }
                    else if (author.getCountry().equals(country)){
                        author.setCountry(country);
                        authorRepository.save(author);
                    }

            });
        }
        return "redirect:/countries";
    }
    //
    @GetMapping("/showFormForUpdateCountry/{id}")
    public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {

        // get employee from the service
        Optional<Country> countryOptional = countryRepository.findById(id);
        Country country = countryOptional.get();
        List<Author> allAuthors = authorRepository.findAll();

        model.addAttribute("country", country);
        model.addAttribute("allAuthors", allAuthors);
        return "update_country";
    }
    //
    @GetMapping("/deleteCountry/{id}")
    public String deleteEmployee(@PathVariable (value = "id") long id) {
        Optional<Country> countryOptional = countryRepository.findById(id);
        Country country = countryOptional.get();
        country.getAuthors().forEach(author -> {
            author.setCountry(null);
            authorRepository.save(author);
        });
        countryRepository.deleteById(id);
        return "redirect:/countries";
    }

}
