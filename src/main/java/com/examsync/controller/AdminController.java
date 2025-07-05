package com.examsync.controller;

import com.examsync.model.Exam;
import com.examsync.service.ExamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")

public class AdminController {

    private final ExamService examService;

    public AdminController(ExamService examService) {
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

        return "admin_dashboard";
    }

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id) {
        examService.approveExam(id);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/reject/{id}")
    public String reject(@PathVariable Long id) {
        examService.rejectExam(id);
        return "redirect:/admin/dashboard";
    }

}
