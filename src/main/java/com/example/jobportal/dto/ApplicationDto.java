package com.example.jobportal.dto;

import com.example.jobportal.enums.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApplicationDto {
    private Long id;
    
    // User information
    private Long userId;
    private String userName;
    private String userEmail;
    
    // Job information
    private Long jobId;
    private String jobTitle;
    private String company;
    private String location;
    
    // Application details
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
    
    // Helper method for display
    public String getStatusDisplay() {
        return status != null ? status.name() : "PENDING";
    }
}
