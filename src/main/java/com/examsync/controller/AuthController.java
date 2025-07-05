package com.examsync.controller;

import com.examsync.dto.RegistrationForm;
import com.examsync.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;

    // --- show blank form ---
    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "register";
    }

    // --- process submitted form ---
    @PostMapping("/register")
    public String processRegister(
            @Valid @ModelAttribute("registrationForm") RegistrationForm form,
            BindingResult br) {

        if (br.hasErrors()) {
            return "register";
        }

        registrationService.save(form);
        return "redirect:/register?success";
    }
}
