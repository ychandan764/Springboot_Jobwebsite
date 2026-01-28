package com.example.jobportal.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomLoginSuccessHandler loginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
               
                .requestMatchers(
                    "/",
                    "/jobs",
                    "/jobs/**",
                    "/login",
                    "/users/register",
                    "/users/register-recruiter",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/error","/about", "/contact", "/privacy-policy"
                ).permitAll()
                
               
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                
                .requestMatchers(
                    "/applications/my-applications",
                    "/applications/withdraw/**"
                ).hasRole("USER")
                
                .requestMatchers("/applications/apply/**").hasRole("USER")
                
                
                .requestMatchers(
                    "/jobs/new",
                    "/jobs/save",
                    "/jobs/edit/**",
                    "/jobs/update/**",
                    "/jobs/delete/**"
                ).hasRole("RECRUITER")
                
                .requestMatchers("/recruiter/**").hasRole("RECRUITER")
                
               
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(loginSuccessHandler)  
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(exception -> exception
                .accessDeniedPage("/error")
            );
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
