package com.example.jobportal.entity;

import com.example.jobportal.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_user_email", columnList = "email", unique = true)
        }
)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;                               // @Email

    @Column(nullable = false)
    private String password;
    
    @Column(name = "resume_path")
    private String resumePath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    
    
}
