package com.olamide.startup_progress_tracker.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = "Full name cannot bey empty")
    @Column(name = "full_name")
    private String fullName;

    @NonNull
    @NotBlank(message = "Email cannot be empty")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NonNull
    @NotBlank(message = "Password cannot be empty")
    @Column(name = "password")
    private String password;
}
