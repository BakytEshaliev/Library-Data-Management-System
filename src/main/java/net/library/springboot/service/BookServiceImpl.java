package net.library.springboot.service;

import java.util.List;
import java.util.Optional;

import net.library.springboot.model.Book;
import net.library.springboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Book getBookById(long id) {
        Optional<Book> optional = bookRepository.findById(id);
        Book book;
        if (optional.isPresent()) {
            book = optional.get();
        } else {
            throw new RuntimeException(" Employee not found for id :: " + id);
        }
        return book;
    }

    @Override
    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }

//	@Override
//	public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
//		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
//			Sort.by(sortField).descending();
//
//		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
//		return this.employeeRepository.findAll(pageable);
//	}
}
