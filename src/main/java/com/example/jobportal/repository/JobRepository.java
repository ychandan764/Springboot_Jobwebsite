package com.example.jobportal.repository;

import com.example.jobportal.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    
    
    Page<Job> findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(
            String title, 
            String location, 
            Pageable pageable
    );
    
    
    List<Job> findByRecruiter_Id(Long recruiterId);
    
     
    long countByRecruiter_Id(Long recruiterId);
    
    
    List<Job> findByCompanyContainingIgnoreCase(String company);
    
    
    List<Job> findByLocationContainingIgnoreCase(String location);
    
    
    List<Job> findTop10ByOrderByCreatedAtDesc();
    
    void deleteByRecruiter_Id(Long recruiterId);
}
