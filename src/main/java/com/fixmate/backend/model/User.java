package com.fixmate.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role; // e.g., "USER", "ADMIN"

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true; // to indicate if the user is active or not
}
