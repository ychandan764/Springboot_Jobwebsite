package com.example.jobportal.controller;

import com.example.jobportal.dto.JobDto;
import com.example.jobportal.entity.User;
import com.example.jobportal.service.JobService;
import com.example.jobportal.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;
    private final UserService userService;

    /**
     * List all jobs with search and pagination
     */
    @GetMapping
    public String jobs(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String location,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        model.addAttribute(
                "jobs",
                jobService.searchJobs(keyword, location, page, 9)
        );
        return "jobs";
    }

    /**
     * View job details
     */
    @GetMapping("/{id}")
    public String jobDetails(@PathVariable Long id, Model model) {
        model.addAttribute("job", jobService.getJobById(id));
        return "job-details";
    }

    /**
     * Show job posting form - for recruiters only
     */
    @GetMapping("/new")
    public String createJobForm(Model model) {
        model.addAttribute("job", new JobDto());
        return "post-job";
    }

    /**
     * Save/create a new job posting
     */
    @PostMapping("/save")
    public String saveJob(
            @Valid @ModelAttribute("job") JobDto jobDto,
            BindingResult result,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("job", jobDto);
            return "post-job";
        }

        try {
            User recruiter = userService.findByEmail(userDetails.getUsername());
            jobService.save(jobDto, recruiter);
            
            redirectAttributes.addFlashAttribute("success", "Job posted successfully!");
            return "redirect:/recruiter/dashboard";
            
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("job", jobDto);
            return "post-job";
        }
    }

    /**
     * Edit job form - for recruiters
     */
    @GetMapping("/edit/{id}")
    public String editJobForm(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {
            User recruiter = userService.findByEmail(userDetails.getUsername());
            JobDto job = jobService.getJobById(id);
            
            // Verify that the job belongs to this recruiter
            if (!job.getRecruiterId().equals(recruiter.getId())) {
                redirectAttributes.addFlashAttribute("error", "You don't have permission to edit this job");
                return "redirect:/recruiter/dashboard";
            }
            
            model.addAttribute("job", job);
            return "post-job";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/recruiter/dashboard";
        }
    }

    /**
     * Update existing job
     */
    @PostMapping("/update/{id}")
    public String updateJob(
            @PathVariable Long id,
            @Valid @ModelAttribute("job") JobDto jobDto,
            BindingResult result,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("job", jobDto);
            return "post-job";
        }

        try {
            User recruiter = userService.findByEmail(userDetails.getUsername());
            jobService.update(id, jobDto, recruiter);
            
            redirectAttributes.addFlashAttribute("success", "Job updated successfully!");
            return "redirect:/recruiter/dashboard";
            
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("job", jobDto);
            return "post-job";
        }
    }

    /**
     * Delete job
     */
    @PostMapping("/delete/{id}")
    public String deleteJob(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes
    ) {
        try {
            User recruiter = userService.findByEmail(userDetails.getUsername());
            jobService.delete(id, recruiter.getId());
            
            redirectAttributes.addFlashAttribute("success", "Job deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/recruiter/dashboard";
    }
}
