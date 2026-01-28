package com.example.jobportal.repository;

import com.example.jobportal.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    
    /**
     * Search jobs by title and location with pagination
     */
    Page<Job> findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(
            String title, 
            String location, 
            Pageable pageable
    );
    
    /**
     * Find all jobs by recruiter
     */
    List<Job> findByRecruiter_Id(Long recruiterId);
    
    /**
     * Count jobs by recruiter
     */
    long countByRecruiter_Id(Long recruiterId);
    
    /**
     * Find jobs by company
     */
    List<Job> findByCompanyContainingIgnoreCase(String company);
    
    /**
     * Find jobs by location
     */
    List<Job> findByLocationContainingIgnoreCase(String location);
    
    /**
     * Find top 10 most recent jobs (for admin dashboard)
     */
    List<Job> findTop10ByOrderByCreatedAtDesc();
    
    /**
     * Delete all jobs by recruiter (for admin operations)
     */
    void deleteByRecruiter_Id(Long recruiterId);
}
