package com.examsync.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Each record represents one exam‑conducting institute.
 */
@Entity
@Table(
        name = "institutes",
        uniqueConstraints = @UniqueConstraint(columnNames = "email")   // no duplicate emails
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Institute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Display / legal name */
    private String name;

    /** Two‑letter or full state name (e.g. "MH" or "Maharashtra") */
    private String state;

    /** Unique login username */
    private String email;

    /** BCrypt‑hashed password */
    private String password;

    /** Institute‑issued code shown on registration form */
    private String code;

    /** Whether an admin has approved this institute to use the system */
    @Builder.Default
    private boolean approved = false;

    /** Soft‑delete / suspension flag */
    @Builder.Default
    private boolean active = true;

    /** Spring‑Security role */
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.ROLE_INSTITUTE;
}
