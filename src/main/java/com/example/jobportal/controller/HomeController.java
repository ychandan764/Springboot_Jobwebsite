package com.example.jobportal.controller;

import com.example.jobportal.entity.User;
import com.example.jobportal.service.JobService;
import com.example.jobportal.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final JobService jobService;
    private final UserService userService;

    
    @GetMapping("/")
    public String home(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String location,
            @RequestParam(defaultValue = "0") int page,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        model.addAttribute(
                "jobs",
                jobService.searchJobs(keyword, location, page, 6)
        );
        
        // Add user name if logged in
        if (userDetails != null) {
            User user = userService.findByEmail(userDetails.getUsername());
            model.addAttribute("userName", user.getName());
        }
        
        return "home";
    }
}
