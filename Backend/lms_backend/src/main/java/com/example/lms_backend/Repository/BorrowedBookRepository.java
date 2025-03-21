package com.example.lms_backend.Repository;
import com.example.lms_backend.model.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    // Additional query methods can be defined here
}