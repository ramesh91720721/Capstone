package com.example.lms_backend.service;

import com.example.lms_backend.model.BorrowedBook;
import com.example.lms_backend.Repository.BorrowedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BorrowedBookService {

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;
    
    /**
     * Records a borrowed book.
     * 
     * @param bookId ID of the book being borrowed
     * @param userId ID of the user borrowing the book (if available)
     * @return true if saved successfully, false otherwise
     */
    public boolean borrowBook(Long bookId, Long userId) {
         try {
              BorrowedBook borrowedBook = new BorrowedBook();
              borrowedBook.setBookId(bookId);
              borrowedBook.setUserId(userId);
              borrowedBook.setBorrowedAt(LocalDateTime.now());
              borrowedBookRepository.save(borrowedBook);
              return true;
         } catch(Exception e) {
              // Log the exception as needed
              e.printStackTrace();
              return false;
         }
    }
}
