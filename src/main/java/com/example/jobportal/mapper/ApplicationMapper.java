package com.example.jobportal.mapper;

import com.example.jobportal.dto.ApplicationDto;
import com.example.jobportal.entity.Application;

public class ApplicationMapper {

    /**
     * Convert Application entity to ApplicationDto
     * Includes user and job information for display purposes
     */
    public static ApplicationDto toDto(Application application) {
        ApplicationDto dto = new ApplicationDto();
        
        // Application details
        dto.setId(application.getId());
        dto.setStatus(application.getStatus());
        dto.setAppliedAt(application.getCreatedAt());
        
        // User information (applicant details)
        if (application.getUser() != null) {
            dto.setUserId(application.getUser().getId());
            dto.setUserName(application.getUser().getName());
            dto.setUserEmail(application.getUser().getEmail());
        }
        
        // Job information
        if (application.getJob() != null) {
            dto.setJobId(application.getJob().getId());
            dto.setJobTitle(application.getJob().getTitle());
            dto.setCompany(application.getJob().getCompany());
            dto.setLocation(application.getJob().getLocation());
        }
        
        return dto;
    }
    
    /**
     * Convert Application entity to ApplicationDto (lightweight version)
     * Use when you don't need full user/job details
     */
    public static ApplicationDto toLightDto(Application application) {
        ApplicationDto dto = new ApplicationDto();
        dto.setId(application.getId());
        dto.setStatus(application.getStatus());
        dto.setAppliedAt(application.getCreatedAt());
        return dto;
    }
}
