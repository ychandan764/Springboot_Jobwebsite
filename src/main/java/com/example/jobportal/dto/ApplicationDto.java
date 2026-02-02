package com.example.jobportal.dto;

import com.example.jobportal.enums.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApplicationDto {
    private Long id;
    
    
    private Long userId;
    private String userName;
    private String userEmail;
    
    
    private Long jobId;
    private String jobTitle;
    private String company;
    private String location;
    
     
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
    
    
    public String getStatusDisplay() {
        return status != null ? status.name() : "PENDING";
    }
}
