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
    public boolean borrowBook(String title, Long userId ) {
         try {
              BorrowedBook borrowedBook = new BorrowedBook();
              
              System.out.println("--------  BorrowedBook called " + title );
              
            //  borrowedBook.setBookId(bookId);
              borrowedBook.setUserId(userId);
              borrowedBook.setBorrowedAt(LocalDateTime.now());
              borrowedBook.setTitle(title);
              borrowedBookRepository.save(borrowedBook);
              return true;
         } catch(Exception e) {
              // Log the exception as needed
              e.printStackTrace();
              return false;
         }
    }
    
//    public boolean returnBook(String title, Long userId ) {
//        try {
//             BorrowedBook borrowedBook = new BorrowedBook();
//             
//             System.out.println("--------  BorrowedBook called " + title );
//             
//           //  borrowedBook.setBookId(bookId);
//             borrowedBook.setUserId(userId);
//             borrowedBook.setBorrowedAt(LocalDateTime.now());
//             borrowedBook.setTitle(title);
//             borrowedBookRepository.save(borrowedBook);
//             return true;
//        } catch(Exception e) {
//             // Log the exception as needed
//             e.printStackTrace();
//             return false;
//        }
//   }
//    
    public boolean returnBook(String title, Long userId) {
        // Find the borrowed book record matching the title and user id.
        // Adjust this query to match your entity fields.
        BorrowedBook record = borrowedBookRepository.findByTitleAndUserId(title, userId);
        if (record == null) {
            System.out.println("No borrowed record found for title: " + title + " and userId: " + userId);
            return false;
        }
        borrowedBookRepository.delete(record);
        return true;
    } 
    
}
