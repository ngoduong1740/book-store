package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    // Basic CRUD Operations
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        // Check if ISBN already exists for new books
        if (book.getId() == null && book.getIsbn() != null) {
            Optional<Book> existingBook = bookRepository.findByIsbn(book.getIsbn());
            if (existingBook.isPresent()) {
                throw new RuntimeException("Book with ISBN " + book.getIsbn() + " already exists");
            }
        }
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        // Update fields
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        book.setPrice(bookDetails.getPrice());
        book.setStockQuantity(bookDetails.getStockQuantity());
        book.setCategory(bookDetails.getCategory());
        book.setDescription(bookDetails.getDescription());

        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        bookRepository.delete(book);
    }

    // Search Operations
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public List<Book> searchBooksByCategory(String category) {
        return bookRepository.findByCategoryIgnoreCase(category);
    }

    public Optional<Book> findBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    // Inventory Operations
    public List<Book> getBooksInStock() {
        return bookRepository.findByStockQuantityGreaterThan(0);
    }

    public List<Book> getOutOfStockBooks() {
        return bookRepository.findByStockQuantityEquals(0);
    }

    public List<Book> getLowStockBooks(Integer threshold) {
        return bookRepository.findLowStockBooks(threshold != null ? threshold : 5);
    }

    public Book updateStock(Long id, Integer newStock) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        if (newStock < 0) {
            throw new RuntimeException("Stock quantity cannot be negative");
        }

        book.setStockQuantity(newStock);
        return bookRepository.save(book);
    }

    // Reporting
    public List<String> getAllCategories() {
        return bookRepository.findAllCategories();
    }

    public Map<String, Object> getInventoryStats() {
        Map<String, Object> stats = new HashMap<>();

        List<Book> allBooks = bookRepository.findAll();
        stats.put("totalBooks", allBooks.size());
        stats.put("booksInStock", bookRepository.findByStockQuantityGreaterThan(0).size());
        stats.put("booksOutOfStock", bookRepository.findByStockQuantityEquals(0).size());
        stats.put("lowStockBooks", bookRepository.findLowStockBooks(5).size());
        stats.put("categories", bookRepository.findAllCategories());

        return stats;
    }
}
