package com.example.lms_backend.controller;


import com.example.lms_backend.model.BorrowedBook;
import com.example.lms_backend.model.BorrowedBookDto;
import com.example.lms_backend.model.Users;
import com.example.lms_backend.Repository.BorrowedBookRepository;
import com.example.lms_backend.service.BorrowedBookService;

import com.example.lms_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class BorrowedBookController {

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;

    @Autowired
    private BorrowedBookService borrowedBookService;

    

    @GetMapping("/borrowed-books")
    public ResponseEntity<?> getBorrowedBooks(@RequestParam(name = "user_id") Long userId) {
        List<BorrowedBook> borrowedBooks = borrowedBookRepository.findByUserId(userId);
        return ResponseEntity.ok(borrowedBooks);
    }
    
    
//    @GetMapping("/api/borrowed-books/fine")
//    public ResponseEntity<List<BorrowedBookDto>> getBorrowedBooks(@RequestParam("user_id") Long userId) {
//        List<BorrowedBook> borrowedBooks = borrowedBookRepository.findByUserId(userId);
//        System.out.println("-------borrowed user ID: " + userId);
//      
//        
//        List<BorrowedBookDto> dtos = borrowedBooks.stream().map(b -> {
//            BorrowedBookDto dto = new BorrowedBookDto();
//            dto.setId(b.getId());
//            dto.setTitle(b.getTitle());  // Ensure your BorrowedBook entity has the title stored
//            dto.setBorrowedAt(b.getBorrowedAt());
//            double fine = borrowedBookService.calculateFine(b);
//            dto.setFine(fine);
//            return dto;
//        }).collect(Collectors.toList());
//        return ResponseEntity.ok(dtos);
//    }
    
    
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
