package com.prepsprint.controller;

import com.prepsprint.dto.LoginRequestDto;
import com.prepsprint.dto.RegisterRequestDto;
import com.prepsprint.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid email or password. Please try again.");
        }
        if (logout != null) {
            model.addAttribute("infoMessage", "You have been logged out successfully.");
        }
        if (!model.containsAttribute("loginDto")) {
            model.addAttribute("loginDto", new LoginRequestDto());
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        if (!model.containsAttribute("registerDto")) {
            model.addAttribute("registerDto", new RegisterRequestDto());
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerDto") RegisterRequestDto dto,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        // Check password matching
        if (dto.getPassword() != null && !dto.getPassword().equals(dto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Passwords do not match");
        }

        // Check email uniqueness
        if (dto.getEmail() != null && authService.isEmailTaken(dto.getEmail())) {
            bindingResult.rejectValue("email", "error.email", "This email address is already registered");
        }

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        authService.registerStudent(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! You can now log in.");
        return "redirect:/auth/login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/auth/login?logout";
    }
}
