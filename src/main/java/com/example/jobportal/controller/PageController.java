package com.example.jobportal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class PageController {

    
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    
    @PostMapping("/contact")
    public String contactSubmit(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam(required = false) String phone,
            @RequestParam String subject,
            @RequestParam String message,
            RedirectAttributes redirectAttributes
    ) {
        try {
            
            System.out.println("Contact Form Submission:");
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("Phone: " + phone);
            System.out.println("Subject: " + subject);
            System.out.println("Message: " + message);
            
            redirectAttributes.addFlashAttribute("success", true);
            return "redirect:/contact";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to send message. Please try again.");
            return "redirect:/contact";
        }
    }

    
    @GetMapping("/privacy-policy")
    public String privacyPolicy() {
        return "privacy-policy";
    }

    
    @GetMapping("/terms")
    public String terms() {
        return "terms";
    }
}
