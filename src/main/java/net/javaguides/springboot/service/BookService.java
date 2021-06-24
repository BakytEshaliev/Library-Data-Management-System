package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    void saveBook(Book book);
    Book getBookById(long id);
    void deleteBookById(long id);
}
