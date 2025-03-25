package com.example.lms_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "fine_config")
public class FineConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Fine rate per minute (e.g., 1.0 = $1 per minute)
    @Column(nullable = false)
    private Double fineRate;

    // getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Double getFineRate() {
        return fineRate;
    }
    public void setFineRate(Double fineRate) {
        this.fineRate = fineRate;
    }
}
