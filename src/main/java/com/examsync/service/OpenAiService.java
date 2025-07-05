package com.examsync.service;

import org.springframework.stereotype.Service;

@Service
public class OpenAiService {

    public String askGpt(String question) {
        // Basic mock responses for testing
        if (question.toLowerCase().contains("upsc")) {
            return "🧠 UPSC exams are typically held in June (Prelims) and September (Mains).";
        } else if (question.toLowerCase().contains("mpsc")) {
            return "📌 MPSC Prelims usually happen around April or May.";
        } else if (question.toLowerCase().contains("exam")) {
            return "📝 Make sure to check our Exam Calendar for upcoming important dates.";
        } else {
            return "🤖 I’m a mock AI for testing. Ask me about exams like UPSC, MPSC, etc.";
        }
    }
}
