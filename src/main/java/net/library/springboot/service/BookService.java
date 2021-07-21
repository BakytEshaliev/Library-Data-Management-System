package net.library.springboot.service;

import net.library.springboot.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    void saveBook(Book book);
    Book getBookById(long id);
    void deleteBookById(long id);
}
