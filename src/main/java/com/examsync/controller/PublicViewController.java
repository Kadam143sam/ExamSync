package com.examsync.controller;

import com.examsync.model.Exam;
import com.examsync.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PublicViewController {

    private final ExamRepository examRepository;

    @Autowired
    public PublicViewController(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @GetMapping("/calendar")
    public String calendarView(Model model) {
        List<Exam> approvedExams = examRepository.findByStatus(Exam.Status.APPROVED);
        model.addAttribute("exams", approvedExams);
        return "public_calendar";
    }



    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard"; // or return a homepage template like "index"
    }

}
