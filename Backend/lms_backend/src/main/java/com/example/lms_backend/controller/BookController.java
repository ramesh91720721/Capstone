package com.example.lms_backend.controller;

import com.example.lms_backend.Repository.BookRepository;
import com.example.lms_backend.model.Book;
import com.example.lms_backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:3000") // Allow React frontend to access API
public class BookController {
	
	 @Autowired
	    private BookService bookService;
	   // private BookRepository bookRepository;
	
	 @GetMapping("/all")
	public List<Book> getBooks()
	{
		return bookService.getAllBooks();
	}
	
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String query) {
        return bookService.searchBooks(query);
    }
	    
   // Save a new book
   @PostMapping
   public Book addBook(@RequestBody Book book) {
       return bookService.saveBook(book);
   }
}




