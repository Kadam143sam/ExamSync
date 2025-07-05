package com.examsync.repository;

import com.examsync.model.Exam;
import com.examsync.model.Institute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findByDate(LocalDate date); // used for conflict detection

    List<Exam> findByInstitute(Institute institute);

    List<Exam> findByStatus(Exam.Status status);

    List<Exam> findByInstituteId(Long instituteId);

    boolean existsByTitleAndDateAndStartTimeAndInstitute(String title, LocalDate date, LocalTime startTime, Institute institute);


}
