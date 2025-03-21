package com.example.lms_backend.controller;

import com.example.lms_backend.Repository.BookRepository;
import com.example.lms_backend.model.Book;
import com.example.lms_backend.model.ApiResponse;
import com.example.lms_backend.model.BorrowBookRequest;
import com.example.lms_backend.service.BookService;
import com.example.lms_backend.service.BorrowedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:3000") // Allow React frontend to access API
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private BookRepository bookRepository;

    //@Autowired
   // private BorrowedBookService borrowedBookService;
    
    // Endpoint to retrieve all books
    @GetMapping("/all")
    public List<Book> getBooks() {
    	 System.out.println("bool all read attempt ");
        return bookService.getAllBooks();
    }
    
    // Endpoint to search books by a query (e.g., title or author)
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String query) {
        return bookService.searchBooks(query);
    }
    
    // Endpoint to add a new book
    @PostMapping("/add")
    public String addBook(@RequestBody Book book) {
        bookRepository.save(book);
        return "Book added successfully";
    }
    
    // Endpoint to update an existing book
    @PutMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setISBN(updatedBook.getISBN());
            book.setAvilable(updatedBook.getAvilable());
            bookRepository.save(book);
            return "Book updated successfully";
        }).orElse("Book not found");
    }
    
    // Endpoint to delete a book
    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return "Book deleted successfully";
        }
        return "Book not found";
    }
    
//    // Endpoint to borrow a book using a request body (with BorrowBookRequest)
//    @PostMapping("/borrow")
//    public ResponseEntity<?> borrowBook(@RequestBody BorrowBookRequest borrowBookRequest) {
//        boolean success = borrowedBookService.borrowBook(borrowBookRequest.getBookId());
//        if (success) {
//            return ResponseEntity.ok(new ApiResponse(true, "Book borrowed successfully!"));
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponse(false, "Failed to borrow the book"));
//        }
//    }
//    
    // Alternative endpoint to borrow a book using a path variable
    @PostMapping("/borrow/{id}")
    public ResponseEntity<String> borrowBookById(@PathVariable Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null && (book.getAvilable() > 0)) {
            // Decrement the available copies by 1
            book.setAvilable(book.getAvilable() - 1);
            bookRepository.save(book);
            return ResponseEntity.ok("Book borrowed successfully");
        }
        return ResponseEntity.badRequest().body("Book is not available");
    }
}
