package com.roi.cartoh.controller;

import com.roi.cartoh.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin
@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // login.html
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {
        boolean hasErrors = false;

        if (username == null || username.trim().isEmpty()) {
            model.addAttribute("usernameError", "Username is required");
            hasErrors = true;
        } else {
            model.addAttribute("username", username); // keep username in form
        }

        if (password == null || password.trim().isEmpty()) {
            model.addAttribute("passwordError", "Password is required");
            hasErrors = true;
        }

        if (hasErrors) {
            return "login";
        }

        if (!userService.checkCredentials(username, password)) {
            model.addAttribute("loginError", "Invalid username or password");
            return "login";
        }

        // Success: Redirect to some secure page or dashboard
        return "redirect:/dashboard";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new Object());
        return "register"; // register.html
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        userService.registerUser(username, password);
        return "redirect:/login";
    }
}
