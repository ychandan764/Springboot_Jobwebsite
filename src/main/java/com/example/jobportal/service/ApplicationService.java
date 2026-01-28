package com.example.jobportal.service;

import com.example.jobportal.dto.ApplicationDto;
import com.example.jobportal.entity.Application;
import com.example.jobportal.entity.Job;
import com.example.jobportal.entity.User;
import com.example.jobportal.enums.ApplicationStatus;
import com.example.jobportal.exception.DuplicateResourceException;
import com.example.jobportal.exception.ResourceNotFoundException;
import com.example.jobportal.exception.UnauthorizedException;
import com.example.jobportal.mapper.ApplicationMapper;
import com.example.jobportal.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    
    /**
     * Apply for a job
     */
    @Transactional
    public void apply(User user, Job job) {
        // Check if user has already applied
        boolean alreadyApplied = applicationRepository.existsByUser_IdAndJob_Id(
                user.getId(),
                job.getId()
        );

        if (alreadyApplied) {
            throw new DuplicateResourceException(
                    "You have already applied for this job"
            );
        }

        Application application = new Application();
        application.setUser(user);
        application.setJob(job);
        application.setStatus(ApplicationStatus.PENDING);

        applicationRepository.save(application);
    }

    /**
     * Get all applications for a specific user
     */
    public List<ApplicationDto> getApplicationsByUser(Long userId) {
        return applicationRepository.findByUser_Id(userId)
                .stream()
                .map(ApplicationMapper::toDto)
                .toList();
    }

    /**
     * Get all applications for a specific job
     */
    public List<ApplicationDto> getApplicationsByJob(Long jobId) {
        return applicationRepository.findByJob_Id(jobId)
                .stream()
                .map(ApplicationMapper::toDto)
                .toList();
    }

    /**
     * Withdraw an application - user can only withdraw their own applications
     */
    @Transactional
    public void withdrawApplication(Long applicationId, Long userId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Application not found with id: " + applicationId
                ));

        // Verify the application belongs to the user
        if (!application.getUser().getId().equals(userId)) {
            throw new UnauthorizedException(
                    "You don't have permission to withdraw this application"
            );
        }

        // Can only withdraw pending applications
        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new IllegalStateException(
                    "You can only withdraw pending applications"
            );
        }

        applicationRepository.delete(application);
    }

    /**
     * Update application status - only recruiters who own the job can do this
     */
    @Transactional
    public void updateStatus(Long applicationId, String status, Long recruiterId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Application not found with id: " + applicationId
                ));

        // Verify the job belongs to the recruiter
        Job job = application.getJob();
        if (!job.getRecruiter().getId().equals(recruiterId)) {
            throw new UnauthorizedException(
                    "You don't have permission to update this application"
            );
        }

        try {
            ApplicationStatus newStatus = ApplicationStatus.valueOf(status.toUpperCase());
            application.setStatus(newStatus);
            applicationRepository.save(application);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }

    /**
     * Get application by ID
     */
    public ApplicationDto getApplicationById(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Application not found with id: " + id
                ));
        
        return ApplicationMapper.toDto(application);
    }

    /**
     * Check if user has applied for a job
     */
    public boolean hasUserApplied(Long userId, Long jobId) {
        return applicationRepository.existsByUser_IdAndJob_Id(userId, jobId);
    }

    /**
     * Count applications for a job
     */
    public long countApplicationsByJob(Long jobId) {
        return applicationRepository.countByJob_Id(jobId);
    }

    /**
     * Count applications by status for a user
     */
    public long countApplicationsByUserAndStatus(Long userId, ApplicationStatus status) {
        return applicationRepository.countByUser_IdAndStatus(userId, status);
    }
}
