package com.example.jobportal.service;

import com.example.jobportal.dto.JobDto;
import com.example.jobportal.entity.Job;
import com.example.jobportal.entity.User;
import com.example.jobportal.exception.ResourceNotFoundException;
import com.example.jobportal.exception.UnauthorizedException;
import com.example.jobportal.mapper.JobMapper;
import com.example.jobportal.repository.JobRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

     
    public Page<JobDto> searchJobs(
            String keyword,
            String location,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        return jobRepository
                .findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(
                        keyword == null ? "" : keyword,
                        location == null ? "" : location,
                        pageable
                )
                .map(JobMapper::toDto);
    }

     
    public Job findEntityById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found with id: " + id));
    }

     
    public JobDto getJobById(Long id) {
        return JobMapper.toDto(findEntityById(id));
    }

     
    @Transactional
    public JobDto save(JobDto dto, User recruiter) {
        Job job = new Job();
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setCompany(dto.getCompany());
        job.setLocation(dto.getLocation());
        job.setRecruiter(recruiter);

        Job savedJob = jobRepository.save(job);
        return JobMapper.toDto(savedJob);
    }

    
    @Transactional
    public JobDto update(Long jobId, JobDto dto, User recruiter) {
        Job job = findEntityById(jobId);

        
        if (!job.getRecruiter().getId().equals(recruiter.getId())) {
            throw new UnauthorizedException(
                    "You don't have permission to update this job"
            );
        }

        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setCompany(dto.getCompany());
        job.setLocation(dto.getLocation());

        Job updatedJob = jobRepository.save(job);
        return JobMapper.toDto(updatedJob);
    }

     
    @Transactional
    public void delete(Long jobId, Long recruiterId) {
        Job job = findEntityById(jobId);

        
        if (!job.getRecruiter().getId().equals(recruiterId)) {
            throw new UnauthorizedException(
                    "You don't have permission to delete this job"
            );
        }

        jobRepository.delete(job);
    }

     
    public List<JobDto> getJobsByRecruiter(Long recruiterId) {
        return jobRepository.findByRecruiter_Id(recruiterId)
                .stream()
                .map(JobMapper::toDto)
                .toList();
    }

     
    public boolean isJobOwnedByRecruiter(Long jobId, Long recruiterId) {
        Job job = findEntityById(jobId);
        return job.getRecruiter().getId().equals(recruiterId);
    }

     
    public long getTotalJobCount() {
        return jobRepository.count();
    }

    
    public long getJobCountByRecruiter(Long recruiterId) {
        return jobRepository.countByRecruiter_Id(recruiterId);
    }

    
    public List<JobDto> getRecentJobs(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by("createdAt").descending());
        return jobRepository.findAll(pageable)
                .map(JobMapper::toDto)
                .getContent();
    }
}
