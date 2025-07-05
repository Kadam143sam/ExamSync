package com.examsync.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Regular public user of ExamSync (students, parents, etc.).
 */
@Entity
@Table(
        name = "app_user",                                  // "user" is reserved in many DBs
        uniqueConstraints = @UniqueConstraint(columnNames = "email")
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;          // unique login

    private String password;       // BCrypt‑hashed

    private String phone;          // optional

    private String state;          // optional: for filtering exams

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.ROLE_USER;
}
