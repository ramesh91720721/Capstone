package com.example.lms_backend.service;

import com.example.lms_backend.model.BorrowedBook;
import com.example.lms_backend.Repository.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BorrowedBookService {

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;

    public boolean borrowBook(Long bookId) {
        // Save the borrowed book information
    	BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setId(bookId);
        borrowedBook.setBorrowedAt(LocalDateTime.now());

        borrowedBookRepository.save(borrowedBook);
        return true;
    }
}
