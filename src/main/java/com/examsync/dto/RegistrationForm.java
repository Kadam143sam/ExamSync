package com.examsync.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationForm {

    public enum UserType { PUBLIC, INSTITUTE }

    @NotBlank                       // " " is rejected
    private String name;

    @Email
    @NotBlank                       // empty string rejected
    private String email;

    @Size(min = 6)                  // e.g. “123456”
    private String password;

    @NotNull                        // must pick PUBLIC or INSTITUTE
    private UserType userType;

    /* public‑user‑only */
    private String phone;

    /* institute‑only */
    private String instituteCode;
    private String state;
}
