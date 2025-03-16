package com.example.lms_backend.service;

import com.example.lms_backend.Repository.*;
import com.example.lms_backend.model.*;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get book by ID
    public Optional<Book> getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    // Add new book
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

//    // Update existing book
//    public Book updateBook(Long bookId, Book updatedBook) {
//        updatedBook.setBookId(bookId);
//        return bookRepository.save(updatedBook);
//    }

    // Delete book by ID
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }
}
  

