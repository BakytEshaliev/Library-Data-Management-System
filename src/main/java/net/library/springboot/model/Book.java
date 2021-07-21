package net.library.springboot.model;

import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToMany(cascade = { CascadeType.REFRESH })
    @JoinTable(
            name = "Book_Author",
            joinColumns = { @JoinColumn(name = "book_id") },
            inverseJoinColumns = { @JoinColumn(name = "author_id") }
    )
    private Set<Author> authors = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "language_id")
    private Language language;

    @ManyToMany(cascade = { CascadeType.REFRESH })
    @JoinTable(
            name = "Book_Genre",
            joinColumns = { @JoinColumn(name = "book_id") },
            inverseJoinColumns = { @JoinColumn(name = "genre_id") }
    )
    private Set<Genre> genres = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public void addAuthor(Author author){
        authors.add(author);
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    @Override
    public String toString(){
        return title;
    }

    public void removeAuthor(Long id) {
        authors.forEach(author -> {
            if (author.getId() == id)
                authors.remove(author);
        });
    }

    public void removeGenre(long id) {
        genres.forEach(genre -> {
            if (genre.getId() == id)
                genres.remove(genre);
        });
    }
}
