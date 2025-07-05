package com.examsync.controller;

import com.examsync.dto.ExamProposalResponse;
import com.examsync.model.Exam;
import com.examsync.service.ExamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping("/propose")
    public ResponseEntity<ExamProposalResponse> proposeExam(@RequestBody Exam exam) {
        ExamProposalResponse response = examService.handleExamProposal(exam);

        if (response.isClash()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping
    public List<Exam> getAll() {
        return examService.getAll();
    }

    @PostMapping("/{id}/approve")
    public Exam approve(@PathVariable Long id) {
        return examService.approveExam(id);
    }

    @PostMapping("/{id}/reject")
    public Exam reject(@PathVariable Long id) {
        return examService.rejectExam(id);
    }

    @GetMapping("/all")
    public List<Map<String, Object>> getAllForCalendar(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String title) {

        return examService.getAll().stream()
                .filter(exam -> {
                    boolean matches = true;
                    if (status != null && !status.isEmpty()) {
                        matches &= exam.getStatus().name().equalsIgnoreCase(status);
                    }
                    if (state != null && !state.isEmpty()) {
                        matches &= exam.getInstitute().getState().equalsIgnoreCase(state);
                    }
                    if (title != null && !title.isEmpty()) {
                        matches &= exam.getTitle().toLowerCase().contains(title.toLowerCase());
                    }
                    return matches;
                })
                .map(exam -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("title", exam.getTitle() + " (" + exam.getStatus() + ")");
                    map.put("start", exam.getDate() + "T" + exam.getStartTime());
                    return map;
                })
                .toList();
    }
}
