package com.example.jobportal.repository;

import com.example.jobportal.entity.Application;
import com.example.jobportal.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    
    
    boolean existsByUser_IdAndJob_Id(Long userId, Long jobId);
    
    
    List<Application> findByUser_Id(Long userId);
    
    
    List<Application> findByJob_Id(Long jobId);
    
    
    long countByJob_Id(Long jobId);
    
    
    long countByUser_IdAndStatus(Long userId, ApplicationStatus status);
    
    
    List<Application> findByStatus(ApplicationStatus status);
    
    
    List<Application> findByUser_IdAndStatus(Long userId, ApplicationStatus status);
    
    
    long countByStatus(ApplicationStatus status);
    
    
    List<Application> findTop10ByOrderByCreatedAtDesc();
    
    
    void deleteByUser_Id(Long userId);
    
     
    void deleteByJob_Id(Long jobId);
}
