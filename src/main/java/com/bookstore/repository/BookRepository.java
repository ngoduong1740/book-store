package com.bookstore.repository;

import com.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Search by title (case-insensitive)
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Search by author (case-insensitive)
    List<Book> findByAuthorContainingIgnoreCase(String author);

    // Search by category
    List<Book> findByCategoryIgnoreCase(String category);

    // Find by ISBN
    Optional<Book> findByIsbn(String isbn);

    // Find books in stock
    List<Book> findByStockQuantityGreaterThan(Integer quantity);

    // Find out of stock books
    List<Book> findByStockQuantityEquals(Integer quantity);

    // Get all categories
    @Query("SELECT DISTINCT b.category FROM Book b WHERE b.category IS NOT NULL ORDER BY b.category")
    List<String> findAllCategories();

    // Get low stock books
    @Query("SELECT b FROM Book b WHERE b.stockQuantity <= :threshold ORDER BY b.stockQuantity ASC")
    List<Book> findLowStockBooks(@Param("threshold") Integer threshold);
}
