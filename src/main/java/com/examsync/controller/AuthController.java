package com.examsync.controller;

import com.examsync.dto.RegistrationForm;
import com.examsync.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;

    @GetMapping("/register")
    public String showRegister(Model model) {
        if (!model.containsAttribute("registrationForm")) {
            model.addAttribute("registrationForm", new RegistrationForm());
        }
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(
            @Valid @ModelAttribute("registrationForm") RegistrationForm form,
            BindingResult br,
            RedirectAttributes redirectAttributes
    ) {
        if (br.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registrationForm", br);
            redirectAttributes.addFlashAttribute("registrationForm", form);
            return "redirect:/register";
        }

        registrationService.save(form);
        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please login.");
        return "redirect:/login";
    }
}
