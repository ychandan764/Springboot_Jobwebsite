package com.example.jobportal.repository;

import com.example.jobportal.entity.User;
import com.example.jobportal.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find user by email
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Count users by role (for admin stats)
     */
    long countByRole(Role role);
    
    /**
     * Find top 10 most recent users
     */
    List<User> findTop10ByOrderByCreatedAtDesc();
    
    /**
     * Find all users by role
     */
    List<User> findByRole(Role role);
}
