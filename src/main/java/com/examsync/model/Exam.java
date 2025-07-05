package com.examsync.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate date;

    private LocalTime startTime;

    private int durationInMinutes;

    @Enumerated(EnumType.STRING)
    private Status status;


    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    // ✅ Getters & Setters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public Status getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStatusBadgeClass() {
        switch (this.status) {
            case APPROVED:
                return "bg-success";
            case PENDING:
                return "bg-warning text-dark";
            case REJECTED:
                return "bg-danger";
            default:
                return "bg-secondary";
        }
    }

    private String conductingBody;
    public String getConductingBody() {
        return conductingBody;
    }

    public void setConductingBody(String conductingBody) {
        this.conductingBody = conductingBody;
    }
}
