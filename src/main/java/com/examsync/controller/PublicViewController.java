package com.examsync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicViewController {

    @GetMapping("/calendar")
    public String calendarView() {
        return "public_calendar"; // maps to templates/public_calendar.html
    }


    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard"; // or return a homepage template like "index"
    }

}
