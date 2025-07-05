package com.examsync.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpscExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer serialNumber;
    private String name;
    private String dateOfNotification;
    private String lastDateToApply;
    private String examDate;
    private String duration;
}
