package com.example.lms_backend.Repository;

import  com.example.lms_backend.model.FineConfig;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FineConfigRepository extends JpaRepository<FineConfig, Long> {
    // For simplicity, assume there is only one configuration record.
    // You could also add a method to find by a specific key if needed.
}
