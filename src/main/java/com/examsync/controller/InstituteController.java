package com.examsync.controller;

import com.examsync.dto.ExamProposalResponse;
import com.examsync.model.Exam;
import com.examsync.model.Institute;
import com.examsync.repository.InstituteRepository;
import com.examsync.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/institute")

public class InstituteController {

    private final ExamService examService;
    private final InstituteRepository instituteRepository;

    public InstituteController(ExamService examService, InstituteRepository instituteRepository) {
        this.examService = examService;
        this.instituteRepository = instituteRepository;
    }

    @GetMapping("/propose")
    public String showForm(Model model, Principal principal) {
        String email = principal.getName();

        Institute institute = instituteRepository.findByEmail(email)
                .orElse(null);

        if (institute == null) {
            // Optional: redirect to access denied or user home page
            return "redirect:/access-denied";
        }

        model.addAttribute("exam", new Exam());
        model.addAttribute("exams", examService.findByInstitute(institute.getId()));

        return "propose_exam";
    }


    @PostMapping("/propose")
    public String handleForm(@ModelAttribute Exam exam, Principal principal, Model model) {
        Institute institute = instituteRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Institute not found"));

        exam.setInstitute(institute);
        ExamProposalResponse response = examService.handleExamProposal(exam);

        if (response.isClash()) {
            model.addAttribute("error", "❌ Exam time overlaps with another scheduled exam. Please choose a different slot.");
            model.addAttribute("suggestions", response.getSuggestedSlots());
            model.addAttribute("sundays", examService.suggestUpcomingFreeSundays(6));
        } else {
            model.addAttribute("status", response.getSavedExam().getStatus());
        }

        model.addAttribute("exam", new Exam());
        model.addAttribute("exams", examService.findByInstitute(institute.getId()));

        return "propose_exam";
    }


}
