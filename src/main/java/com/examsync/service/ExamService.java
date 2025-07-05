package com.examsync.service;

import com.examsync.dto.ExamProposalResponse;
import com.examsync.model.Exam;
import com.examsync.repository.ExamRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExamService {

    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public List<Exam> getAll() {
        return examRepository.findAll();
    }

    public ExamProposalResponse handleExamProposal(Exam exam) {
        boolean clash = hasClash(exam);

        if (clash) {
            List<String> suggestions = suggestAvailableSlots(exam.getDate(), exam.getDurationInMinutes());
            return new ExamProposalResponse(true, null, suggestions);
        }

        exam.setStatus(Exam.Status.PENDING);
        Exam saved = examRepository.save(exam);
        return new ExamProposalResponse(false, saved, null);
    }

    public boolean hasClash(Exam newExam) {
        List<Exam> sameDayExams = examRepository.findByDate(newExam.getDate());

        LocalTime newStart = newExam.getStartTime();
        LocalTime newEnd = newStart.plusMinutes(newExam.getDurationInMinutes());

        for (Exam existing : sameDayExams) {
            LocalTime existingStart = existing.getStartTime();
            LocalTime existingEnd = existingStart.plusMinutes(existing.getDurationInMinutes());

            if (newStart.isBefore(existingEnd) && existingStart.isBefore(newEnd)) {
                return true;
            }
        }

        return false;
    }

    public List<String> suggestAvailableSlots(LocalDate date, int durationInMinutes) {
        List<String> availableSlots = new ArrayList<>();

        LocalTime dayStart = LocalTime.of(9, 0);
        LocalTime dayEnd = LocalTime.of(18, 0);
        List<Exam> sameDayExams = examRepository.findByDate(date);

        for (LocalTime start = dayStart;
             start.plusMinutes(durationInMinutes).isBefore(dayEnd.plusSeconds(1));
             start = start.plusMinutes(30)) {

            LocalTime end = start.plusMinutes(durationInMinutes);
            boolean clash = false;

            for (Exam existing : sameDayExams) {
                LocalTime existingStart = existing.getStartTime();
                LocalTime existingEnd = existingStart.plusMinutes(existing.getDurationInMinutes());

                if (start.isBefore(existingEnd) && existingStart.isBefore(end)) {
                    clash = true;
                    break;
                }
            }

            if (!clash) {
                availableSlots.add(start + " - " + end);
            }
        }

        return availableSlots;
    }

    public Exam approveExam(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        exam.setStatus(Exam.Status.APPROVED);
        return examRepository.save(exam);
    }

    public Exam rejectExam(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        exam.setStatus(Exam.Status.REJECTED);
        return examRepository.save(exam);
    }

    public List<Exam> findByInstitute(Long instituteId) {
        return examRepository.findByInstituteId(instituteId);
    }

    public List<Exam> getAllPending() {
        return examRepository.findByStatus(Exam.Status.PENDING);
    }

    public boolean isDuplicate(Exam newExam) {
        return examRepository.existsByTitleAndDateAndStartTimeAndInstitute(
                newExam.getTitle(),
                newExam.getDate(),
                newExam.getStartTime(),
                newExam.getInstitute()
        );
    }

    public List<LocalDate> suggestUpcomingFreeSundays(int weeksAhead) {
        List<LocalDate> freeSundays = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = 1; i <= weeksAhead; i++) {
            LocalDate sunday = today.plusWeeks(i).with(java.time.DayOfWeek.SUNDAY);
            List<Exam> examsOnSunday = examRepository.findByDate(sunday);
            if (examsOnSunday.isEmpty()) {
                freeSundays.add(sunday);
            }
        }

        return freeSundays;
    }

    public long countByStatus(Exam.Status status) {
        return examRepository.findAll().stream()
                .filter(exam -> exam.getStatus() == status)
                .count();
    }

}
