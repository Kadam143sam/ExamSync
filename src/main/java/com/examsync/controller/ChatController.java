package com.examsync.controller;

import com.examsync.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final OpenAiService openAiService;

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest req) {
        return openAiService.askGpt(req.message());
    }

    public record ChatRequest(String message) {}
}

