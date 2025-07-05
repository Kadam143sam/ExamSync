package com.examsync.dto;

import com.examsync.model.Exam;
import java.util.List;

public class ExamProposalResponse {
    private boolean clash;
    private Exam savedExam;
    private List<String> suggestedSlots;

    public ExamProposalResponse() {}

    public ExamProposalResponse(boolean clash, Exam savedExam, List<String> suggestedSlots) {
        this.clash = clash;
        this.savedExam = savedExam;
        this.suggestedSlots = suggestedSlots;
    }

    public boolean isClash() {
        return clash;
    }

    public void setClash(boolean clash) {
        this.clash = clash;
    }

    public Exam getSavedExam() {
        return savedExam;
    }

    public void setSavedExam(Exam savedExam) {
        this.savedExam = savedExam;
    }

    public List<String> getSuggestedSlots() {
        return suggestedSlots;
    }

    public void setSuggestedSlots(List<String> suggestedSlots) {
        this.suggestedSlots = suggestedSlots;
    }
}
