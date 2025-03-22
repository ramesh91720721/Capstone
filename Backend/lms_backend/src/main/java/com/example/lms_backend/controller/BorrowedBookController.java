package com.example.lms_backend.controller;


import com.example.lms_backend.model.BorrowedBook;
import com.example.lms_backend.model.Users;
import com.example.lms_backend.Repository.BorrowedBookRepository;
import com.example.lms_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class BorrowedBookController {

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;


    @GetMapping("/borrowed-books")
    public ResponseEntity<?> getBorrowedBooks(@RequestParam(name = "user_id") Long userId) {
        List<BorrowedBook> borrowedBooks = borrowedBookRepository.findByUserId(userId);
        return ResponseEntity.ok(borrowedBooks);
    }
    
//    @GetMapping("/borrowed-books")
//    public ResponseEntity<?> getBorrowedBooks(@RequestParam long userid){
//        // Look up the user by username
////        Users user = userRepository.findByUsername(username);
////        if (user == null) {
////            return ResponseEntity.status(HttpStatus.NOT_FOUND)
////                                 .body("User not found");
////        }
//        // Get borrowed books for the user
//    	
//    	System.out.println("-------borrowed user ID: " + userid);
//        List<BorrowedBook> borrowedBooks = borrowedBookRepository.findByUserId(userid);
//        return ResponseEntity.ok(borrowedBooks);
//    }
}
