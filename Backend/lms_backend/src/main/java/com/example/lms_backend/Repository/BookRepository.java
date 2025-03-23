package com.example.lms_backend.Repository;

import com.example.lms_backend.model.Book;
import java.util.List;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingOrAuthorContainingOrIsbnContaining(String title, String author, String ISBN);
    
    Optional<Book> findByTitle(String title);
}
 