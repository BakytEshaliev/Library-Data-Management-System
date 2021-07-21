package net.library.springboot.repository;

import net.library.springboot.model.Author;
import net.library.springboot.model.Book;
import net.library.springboot.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBookByAuthorsContains(Author a);
    List<Book> findBookByGenresContains(Genre g);
    List<Book> findBookByLanguage_Id(Long id);
}
