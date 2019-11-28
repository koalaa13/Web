package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.service.UserService;

@Controller
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public String user(@PathVariable("userId") String userId, Model model) {
        try {
            long id = Long.parseLong(userId);
            model.addAttribute("user", userService.findById(id));
        } catch (NumberFormatException ignored) {
            // No operations.
        }
        return "UserPage";
    }
}
