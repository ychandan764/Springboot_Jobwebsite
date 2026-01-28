package com.example.jobportal.repository;

import com.example.jobportal.entity.Application;
import com.example.jobportal.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    
    /**
     * Check if user has already applied for a job
     */
    boolean existsByUser_IdAndJob_Id(Long userId, Long jobId);
    
    /**
     * Find all applications by user
     */
    List<Application> findByUser_Id(Long userId);
    
    /**
     * Find all applications for a specific job
     */
    List<Application> findByJob_Id(Long jobId);
    
    /**
     * Count applications for a specific job
     */
    long countByJob_Id(Long jobId);
    
    /**
     * Count applications by user and status
     */
    long countByUser_IdAndStatus(Long userId, ApplicationStatus status);
    
    /**
     * Find applications by status
     */
    List<Application> findByStatus(ApplicationStatus status);
    
    /**
     * Find applications by user and status
     */
    List<Application> findByUser_IdAndStatus(Long userId, ApplicationStatus status);
    
    /**
     * Count applications by status (for admin stats)
     */
    long countByStatus(ApplicationStatus status);
    
    /**
     * Find top 10 most recent applications (for admin dashboard)
     */
    List<Application> findTop10ByOrderByCreatedAtDesc();
    
    /**
     * Delete all applications by user (for admin operations)
     */
    void deleteByUser_Id(Long userId);
    
    /**
     * Delete all applications for a job (for admin operations)
     */
    void deleteByJob_Id(Long jobId);
}
