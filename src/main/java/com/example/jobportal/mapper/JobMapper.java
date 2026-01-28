package com.example.jobportal.mapper;

import com.example.jobportal.dto.JobDto;
import com.example.jobportal.entity.Job;

public class JobMapper {

    /**
     * Convert Job entity to JobDto
     * Includes recruiter information for authorization checks
     */
    public static JobDto toDto(Job job) {
        JobDto dto = new JobDto();
        
        // Basic job information
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setCompany(job.getCompany());
        dto.setLocation(job.getLocation());
        
        // Timestamps
        dto.setCreatedAt(job.getCreatedAt());
        dto.setUpdatedAt(job.getUpdatedAt());
        
        // Recruiter information (for authorization and display)
        if (job.getRecruiter() != null) {
            dto.setRecruiterId(job.getRecruiter().getId());
            dto.setRecruiterName(job.getRecruiter().getName());
        }
        
        return dto;
    }
    
    /**
     * Convert Job entity to JobDto (lightweight version)
     * Use when you don't need recruiter details
     */
    public static JobDto toLightDto(Job job) {
        JobDto dto = new JobDto();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setCompany(job.getCompany());
        dto.setLocation(job.getLocation());
        return dto;
    }
}
