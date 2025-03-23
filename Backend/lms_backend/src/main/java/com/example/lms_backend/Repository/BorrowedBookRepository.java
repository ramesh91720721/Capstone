package com.example.lms_backend.Repository;

import com.example.lms_backend.model.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    List<BorrowedBook> findByUserId(Long userId);
    BorrowedBook findByTitleAndUserId(String title, Long userId);
}
