package com.examsync.controller;

import com.examsync.model.UpscExam;
import com.examsync.repository.UpscExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller

public class UpscController {

    private final UpscExamRepository examRepository;

    public UpscController(UpscExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @GetMapping("/upsc/calendar")
    public String showCalendar(Model model) {
        List<UpscExam> exams = examRepository.findAll();
        model.addAttribute("exams", exams);
        return "upsc_calendar"; // This refers to upsc_calendar.html
    }
}
