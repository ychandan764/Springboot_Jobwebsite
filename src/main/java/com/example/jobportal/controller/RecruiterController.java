package com.example.jobportal.controller;

import com.example.jobportal.entity.User;
import com.example.jobportal.service.ApplicationService;
import com.example.jobportal.service.JobService;
import com.example.jobportal.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recruiter")
public class RecruiterController {

    private final JobService jobService;
    private final UserService userService;
    private final ApplicationService applicationService;

    /**
     * Recruiter dashboard - shows all jobs posted by the recruiter
     */
    @GetMapping("/dashboard")
    public String dashboard(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        User recruiter = userService.findByEmail(userDetails.getUsername());
        
        model.addAttribute(
                "jobs",
                jobService.getJobsByRecruiter(recruiter.getId())
        );
        
        model.addAttribute("recruiterName", recruiter.getName());
        
        return "recruiter-dashboard";
    }

    /**
     * View applicants for a specific job
     */
    @GetMapping("/job/{jobId}/applicants")
    public String viewApplicants(
            @PathVariable Long jobId,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {
            User recruiter = userService.findByEmail(userDetails.getUsername());
            
            // Verify the job belongs to this recruiter
            if (!jobService.isJobOwnedByRecruiter(jobId, recruiter.getId())) {
                redirectAttributes.addFlashAttribute("error", "You don't have permission to view these applicants");
                return "redirect:/recruiter/dashboard";
            }
            
            model.addAttribute("job", jobService.getJobById(jobId));
            model.addAttribute(
                    "applications", 
                    applicationService.getApplicationsByJob(jobId)
            );
            
            return "job-applicants";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/recruiter/dashboard";
        }
    }

    /**
     * Update application status
     */
    @GetMapping("/application/{applicationId}/status/{status}")
    public String updateApplicationStatus(
            @PathVariable Long applicationId,
            @PathVariable String status,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes
    ) {
        try {
            User recruiter = userService.findByEmail(userDetails.getUsername());
            applicationService.updateStatus(applicationId, status, recruiter.getId());
            
            redirectAttributes.addFlashAttribute("success", "Application status updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:" + redirectAttributes.getAttribute("referer");
    }
}
