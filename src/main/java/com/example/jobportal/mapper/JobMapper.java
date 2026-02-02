package com.example.jobportal.mapper;

import com.example.jobportal.dto.JobDto;
import com.example.jobportal.entity.Job;

public class JobMapper {

   
    public static JobDto toDto(Job job) {
        JobDto dto = new JobDto();
        
         
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setCompany(job.getCompany());
        dto.setLocation(job.getLocation());
        
         
        dto.setCreatedAt(job.getCreatedAt());
        dto.setUpdatedAt(job.getUpdatedAt());
        
       
        if (job.getRecruiter() != null) {
            dto.setRecruiterId(job.getRecruiter().getId());
            dto.setRecruiterName(job.getRecruiter().getName());
        }
        
        return dto;
    }
    
    
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
