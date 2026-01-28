package com.example.jobportal.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;


@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        
        // Redirect to appropriate dashboard based on role
        if (roles.contains("ROLE_ADMIN")) {
            // Admins go to admin dashboard
            response.sendRedirect("/admin/dashboard");
        } else if (roles.contains("ROLE_RECRUITER")) {
            // Recruiters go to their dashboard
            response.sendRedirect("/recruiter/dashboard");
        } else if (roles.contains("ROLE_USER")) {
            // Job seekers go to browse jobs
            response.sendRedirect("/jobs");
        } else {
            // Default redirect to home
            response.sendRedirect("/");
        }
    }
}
