package com.examsync.service;

import com.examsync.model.UpscExam;
import com.examsync.repository.UpscExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class UpscCalendarService {

    private final UpscExamRepository examRepository;

    public UpscCalendarService(UpscExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public void saveExamsFromCsv(InputStream csvStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvStream, StandardCharsets.UTF_8))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header
                    continue;
                }

                String[] fields = line.split(",", -1); // Include empty strings

                UpscExam exam = UpscExam.builder()
                        .serialNumber(parseInteger(fields[0]))
                        .name(fields[1])
                        .dateOfNotification(fields[2])
                        .lastDateToApply(fields[3])
                        .examDate(fields[4])
                        .duration(fields[5])
                        .build();

                examRepository.save(exam);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage());
        }
    }

    private Integer parseInteger(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            return null;
        }
    }
}
