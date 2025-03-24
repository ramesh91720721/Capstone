package com.example.lms_backend.controller;

import com.example.lms_backend.Repository.BookRepository;
import com.example.lms_backend.model.Book;
import com.example.lms_backend.model.ApiResponse;
import com.example.lms_backend.model.BorrowBookRequest;
import com.example.lms_backend.model.Users;
import com.example.lms_backend.service.BookService;
import com.example.lms_backend.service.BorrowedBookService;
import com.example.lms_backend.Repository.BorrowedBookRepository;

import jakarta.servlet.http.HttpSession;

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

    @Autowired
    private BorrowedBookService borrowedBookService;
    
    @Autowired
    private BorrowedBookRepository borrowedBookRepository;
    
    // Endpoint to retrieve all books
    @GetMapping("/all")
    public List<Book> getBooks() {
    	 System.out.println("bool all read attempt ");
        return bookService.getAllBooks();
    }
    
    // Endpoint to search books by a query (e.g., title or author)
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String query) {
        return bookService.searchBooks(query );
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
//    // Alternative endpoint to borrow a book using a path variable
//    @PostMapping("/borrow/{id}")
//    public ResponseEntity<String> borrowBookById(@PathVariable long id) {
//    	
//    	 System.out.println("borrow ID: " + id);
//        Book book = bookRepository.findById((long) id).orElse(null);
//        if (book != null && (book.getAvilable() > 0)) {
//            // Decrement the available copies by 1
//            book.setAvilable(book.getAvilable() - 1);
//            bookRepository.save(book);
//            return ResponseEntity.ok("Book borrowed successfully");
//        }
//        return ResponseEntity.badRequest().body("Book is not available");
//    }
    
    // Borrow a book using the path variable and record the borrowed entry.
    @PostMapping("/borrow/{id}")
    public ResponseEntity<String> borrowBookById(@PathVariable Long id, HttpSession session) {
        // Retrieve the user from session (assumes you have set this during login)
    	 System.out.println("-------borrow ID: " + id);
    	 
        Users user = (Users) session.getAttribute("user");
       // System.out.println("------userid" + user.getId());
        
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
//        }
        
     // Check if the user already borrowed 3 books
        int currentBorrowCount = borrowedBookRepository.countByUserId((long)1);
        if (currentBorrowCount >= 3) {
            return ResponseEntity.badRequest().body("Maximum borrow limit reached (3 books).");
        }
        
         
        
        Book book = bookRepository.findById(id).orElse(null);
        System.out.println("------book avilable " + book.getAvilable());
        if (book != null && (book.getAvilable() > 0)) {
            // Decrement the available count by 1
            book.setAvilable(book.getAvilable() - 1);
            bookRepository.save(book);
            
            System.out.println("bookRepository.save(book); called "  );
            // Record the borrowed book in the borrowed_books table
            boolean recordSaved = borrowedBookService.borrowBook(book.getTitle(), (long)1   );
            if (recordSaved) {
                return ResponseEntity.ok("Book borrowed successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to record the borrowed book");
            }
        }
        return ResponseEntity.badRequest().body("Book is not available");
    }
    
    @PostMapping("/return/{title}")
    public ResponseEntity<String> returnBookByTitle(@PathVariable String title, HttpSession session) {
        System.out.println("-------Return title: " + title);
        
        // Retrieve the logged-in user from the session
//        Users user = (Users) session.getAttribute("user");
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
//        }
       // System.out.println("-------get users: " + title);
        // Find the book using the title
        Book book = bookRepository.findByTitle(title).orElse(null);
        if (book == null) {
            return ResponseEntity.badRequest().body("Book not found");
        }
        
        // Increase the available count (assuming returning the book makes it available again)
        book.setAvilable(book.getAvilable() + 1);
        bookRepository.save(book);
        
        // Record the return using the book title and the user's id
        boolean recordReturned = borrowedBookService.returnBook(title,(long)1);
        if (recordReturned) {
            return ResponseEntity.ok("Book returned successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to record the book return");
        }
    }

//    @PostMapping("/return/{title}")
//    public ResponseEntity<String> returnBook(@PathVariable String title, HttpSession session) {
//        // Retrieve the user from session and process the return
//        Users user = (Users) session.getAttribute("user");
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
//        }
//        
//        // Retrieve the BorrowedBook record and process the return logic:
//        // 1. Increase the available count for the book.
//        // 2. Delete or update the BorrowedBook record.
//         Book book = bookRepository.findByTitle(title).orElse(null);
//        
//        
//        boolean success = borrowedBookService.returnBook(title, (long)1  );
//        if (success) {
//            return ResponseEntity.ok("Book returned successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to return book");
//        }
//    }
}
