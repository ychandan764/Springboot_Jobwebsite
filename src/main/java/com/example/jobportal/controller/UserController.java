package com.example.jobportal.controller;

import com.example.jobportal.dto.UserDto;
import com.example.jobportal.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @GetMapping("/register")
    public String userRegisterForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "register";
        }

        userService.registerUser(userDto);
        return "redirect:/login";
    }

    

    @GetMapping("/register-recruiter")
    public String recruiterRegisterForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register-recruiter";
    }

    @PostMapping("/register-recruiter")
    public String registerRecruiter(
            @Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "register-recruiter";
        }

        userService.registerRecruiter(userDto);
        return "redirect:/login";
    }
}

