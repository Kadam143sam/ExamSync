package com.examsync.controller;

import com.examsync.model.Exam;
import com.examsync.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List; // ✅ Missing import
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    private final ExamService examService;

    public DashboardController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());

        List<Exam> allExams = examService.getAll();
        model.addAttribute("exams", allExams);

        long total = allExams.size();
        long approved = allExams.stream().filter(e -> e.getStatus() == Exam.Status.APPROVED).count();
        long pending = allExams.stream().filter(e -> e.getStatus() == Exam.Status.PENDING).count();
        long rejected = allExams.stream().filter(e -> e.getStatus() == Exam.Status.REJECTED).count();

        model.addAttribute("total", total);
        model.addAttribute("approved", approved);
        model.addAttribute("pending", pending);
        model.addAttribute("rejected", rejected);

        // ✅ Next upcoming exam
        Optional<Exam> nextExam = allExams.stream()
                .filter(e -> e.getDate() != null && e.getDate().isAfter(LocalDate.now()))
                .sorted(Comparator.comparing(Exam::getDate))
                .findFirst();

        model.addAttribute("nextExam", nextExam.orElse(null));

        // ✅ All upcoming exams within 1 year
        List<Exam> upcomingExams = allExams.stream()
                .filter(e -> e.getDate() != null && !e.getDate().isBefore(LocalDate.now()))
                .filter(e -> e.getDate().isBefore(LocalDate.now().plusYears(1)))
                .sorted(Comparator.comparing(Exam::getDate))
                .collect(Collectors.toList());

        model.addAttribute("upcomingExams", upcomingExams);

        return "dashboard";
    }

}
