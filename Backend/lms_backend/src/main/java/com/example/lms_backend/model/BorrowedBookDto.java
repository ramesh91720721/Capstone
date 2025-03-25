package com.example.lms_backend.model;

import java.time.LocalDateTime;
import java.util.Date;

public class BorrowedBookDto {
    private Long id;
    private String title;
    private LocalDateTime borrowedAt;
    private double fine; // calculated overdue fine

    // getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public LocalDateTime getBorrowedAt() {
        return borrowedAt;
    }
    public void setBorrowedAt(LocalDateTime borrowedAt) {
        this.borrowedAt = borrowedAt;
    }
    public double getFine() {
        return fine;
    }
    public void setFine(double fine) {
        this.fine = fine;
    }
}
