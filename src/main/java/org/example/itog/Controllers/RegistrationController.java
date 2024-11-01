package org.example.itog.Controllers;

import org.example.itog.ApplicationService;
import org.example.itog.Models.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new MyUser());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") MyUser user) {
        user.setName(user.getName());
        user.setPassword(user.getPassword());
        user.setRole("USER");
        applicationService.addUser(user);
        return "redirect:/home";
    }
}
