package com.examsync.model;

/**
 * Application‑wide roles. Spring Security expects the
 * values to start with "ROLE_" by convention.
 */
public enum Role {
    ROLE_USER,
    ROLE_INSTITUTE,
    ROLE_ADMIN          // keep for future admin accounts
}
