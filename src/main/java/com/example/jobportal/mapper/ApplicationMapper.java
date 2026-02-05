package com.example.jobportal.mapper;

import com.example.jobportal.dto.ApplicationDto;
import com.example.jobportal.entity.Application;

public class ApplicationMapper {

    
    public static ApplicationDto toDto(Application application) {
        ApplicationDto dto = new ApplicationDto();
        
        
        dto.setId(application.getId());
        dto.setStatus(application.getStatus());
        dto.setAppliedAt(application.getCreatedAt());
        
         
        if (application.getUser() != null) {
            dto.setUserId(application.getUser().getId());
            dto.setUserName(application.getUser().getName());
            dto.setUserEmail(application.getUser().getEmail());
        }
        
        
        if (application.getJob() != null) {
            dto.setJobId(application.getJob().getId());
            dto.setJobTitle(application.getJob().getTitle());
            dto.setCompany(application.getJob().getCompany());
            dto.setLocation(application.getJob().getLocation());
        }
        
        return dto;
    }
    
    
    public static ApplicationDto toLightDto(Application application) {
        ApplicationDto dto = new ApplicationDto();
        dto.setId(application.getId());
        dto.setStatus(application.getStatus());
        dto.setAppliedAt(application.getCreatedAt());
        return dto;
    }
}
