package net.javaguides.springboot;

import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.model.*;
import net.javaguides.springboot.repository.*;
import net.javaguides.springboot.security.domain.Role;
import net.javaguides.springboot.security.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@Slf4j
public class SpringbootThymeleafCrudWebAppApplication {
	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private GenreRepository genreRepository;

	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private TypeRepository typeRepository;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootThymeleafCrudWebAppApplication.class, args);
	}

	@Bean
	CommandLineRunner init(){
		return (exp)->{
			Author author = new Author();
			author.setFirstName("Boris");
			author.setLastName("Strugatskiy");

			Author author1 = new Author();
			author1.setFirstName("Arkadiy");
			author1.setLastName("Strugatskiy");

			Author author2 = new Author();
			author2.setFirstName("Chuck");
			author2.setLastName("Palahniuk");

			Author author3 = new Author();
			author3.setFirstName("Terry");
			author3.setLastName("Pratchett");

			Author author4 = new Author();
			author4.setFirstName("Arthur");
			author4.setLastName("Conan Doyle");

			Author author5 = new Author();
			author5.setFirstName("Haruki");
			author5.setLastName("Nurakami");

			authorRepository.save(author);
			authorRepository.save(author1);
			authorRepository.save(author2);
			authorRepository.save(author3);
			authorRepository.save(author4);
			authorRepository.save(author5);

			Type type = new Type();
			type.setName("E-book");

			Type type1 = new Type();
			type1.setName("Paper");

			typeRepository.save(type);
			typeRepository.save(type1);

			Language language = new Language();
			language.setName("Russian");
			languageRepository.save(language);

			Language language1 = new Language();
			language1.setName("English");
			languageRepository.save(language1);

			Language language2 = new Language();
			language2.setName("German");
			languageRepository.save(language2);

			Country country = new Country();
			country.setName("Kyrgyzstan");
			countryRepository.save(country);

			Country country1 = new Country();
			country1.setName("Russia");
			countryRepository.save(country1);

			Country country2 = new Country();
			country2.setName("Germany");
			countryRepository.save(country2);

			Country country3 = new Country();
			country3.setName("Great Britain");
			countryRepository.save(country3);

			Country country4 = new Country();
			country4.setName("USA");
			countryRepository.save(country4);

			Genre genre = new Genre();
			genre.setName("Fantasy");
			genreRepository.save(genre);

			Genre genre1 = new Genre();
			genre1.setName("Detective");
			genreRepository.save(genre1);

			Genre genre2 = new Genre();
			genre2.setName("Fantastic");
			genreRepository.save(genre2);

			Genre genre3 = new Genre();
			genre3.setName("Triller");
			genreRepository.save(genre3);

			Genre genre4 = new Genre();
			genre4.setName("Comedy");
			genreRepository.save(genre4);

			Book book1 = new Book();
			book1.setTitle("Hard to be a god");
			bookRepository.save(book1);

			Book book2 = new Book();
			book2.setTitle("Hard to be a god 1");
			bookRepository.save(book2);

			Book book3 = new Book();
			book3.setTitle("Hard to be a god 2");
			bookRepository.save(book3);

			PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
			User u1 = new User();
			u1.setLoginName("reader");
			u1.setPasswordHash(passwordEncoder.encode("a"));
			u1.setRole(Role.READER);
			userRepository.save(u1);

			User u2 = new User();
			u2.setLoginName("reader_writer");
			u2.setPasswordHash(passwordEncoder.encode("b"));
			u2.setRole(Role.READ_WRITER);
			userRepository.save(u2);

			User u3 = new User();
			u3.setLoginName("admin");
			u3.setPasswordHash(passwordEncoder.encode("c"));
			u3.setRole(Role.ADMIN);
			userRepository.save(u3);

//			Optional<Book> bookOptional = bookRepository.findById(book1.getId());
//			Book book = bookOptional.get();
//			Optional<Author> authorBookOpt = authorRepository.findById(author.getId());
//			Author authorBook = authorBookOpt.get();
//
//			book.addAuthor(authorBook);
			//author.addBook(book);

//			book.addAuthor(author1);
//			//author1.addBook(book);
//
//			book.setType(type1);
//			//type1.addBook(book);
//
//			book.setLanguage(language);
//			//language.addBook(book);
//
//			book.addGenre(genre);
//			//genre.addBook(book);
//
//			book.addGenre(genre2);
////			//genre2.addBook(book);
////
////			book.addGenre(genre3);
////			//genre3.addBook(book);
//			bookRepository.save(book);
		};
	}
}
