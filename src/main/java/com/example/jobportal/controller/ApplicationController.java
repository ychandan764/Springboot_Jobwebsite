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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final JobService jobService;
    private final UserService userService;
    
    @GetMapping("/apply/{jobId}")
    public String initiateApplication(
            @PathVariable Long jobId,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes
    ) {
        User user = userService.findByEmail(userDetails.getUsername());
        
        // Check if user has resume
        if (user.getResumePath() == null || user.getResumePath().isEmpty()) {
            // Redirect to resume upload
            return "redirect:/resume-upload/" + jobId;
        }
        
        // User has resume, proceed with application
        return applyForJob(jobId, userDetails, redirectAttributes);
    }

    /**
     * Apply for a job - requires authentication
     */
    @PostMapping("/apply/{jobId}")
    public String applyForJob(
            @PathVariable Long jobId,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes
    ) {
        try {
            // Get authenticated user
            User user = userService.findByEmail(userDetails.getUsername());
            
            // Apply for the job
            applicationService.apply(user, jobService.findEntityById(jobId));
            
            redirectAttributes.addFlashAttribute("success", "Application submitted successfully!");
            return "redirect:/jobs/" + jobId;
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/jobs/" + jobId;
        }
    }

    /**
     * View user's own applications - requires authentication
     */
    @GetMapping("/my-applications")
    public String myApplications(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        User user = userService.findByEmail(userDetails.getUsername());
        
        model.addAttribute(
                "applications", 
                applicationService.getApplicationsByUser(user.getId())
        );
        
        return "my-applications";
    }

    /**
     * Withdraw an application - requires authentication
     */
    @PostMapping("/withdraw/{applicationId}")
    public String withdrawApplication(
            @PathVariable Long applicationId,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes
    ) {
        try {
            User user = userService.findByEmail(userDetails.getUsername());
            applicationService.withdrawApplication(applicationId, user.getId());
            
            redirectAttributes.addFlashAttribute("success", "Application withdrawn successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/applications/my-applications";
    }
}
