package com.example.lms_backend.service;

import com.example.lms_backend.model.BorrowedBook;
import com.example.lms_backend.model.FineConfig;
import com.example.lms_backend.Repository.BorrowedBookRepository;
import com.example.lms_backend.Repository.FineConfigRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowedBookService {

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;
    
    @Autowired
    private FineConfigRepository fineConfigRepository;
    
    
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
    
    public int calculateFine(BorrowedBook borrowedBook) {
        // Retrieve fine configuration (assuming one record exists)
        FineConfig config = fineConfigRepository.findAll().stream().findFirst().orElse(null);
        int rate = (int) ((config != null) ? config.getFineRate() : 1); // default $1 per minute if none configured
        
        // Calculate elapsed time in minutes
        //long diffInMillis = new Date().getTime() - borrowedBook.getBorrowedAt().getTime();
        long diffInMillis = new Date().getTime() - 
        	    Date.from(borrowedBook.getBorrowedAt().atZone(ZoneId.systemDefault()).toInstant()).getTime();
        
        double elapsedMinutes = diffInMillis / (1000.0 * 60);
        
        // Subtract the 10-minute grace period
        double overdueMinutes = elapsedMinutes ;
        return (int)( (overdueMinutes > 0) ? overdueMinutes * rate : 0);
   }
    
}
