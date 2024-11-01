package org.example.itog.Controllers;

import org.example.itog.Models.MyUser;
import org.example.itog.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/user")
public class MyUserViewController {

    private final UserRepository userService;

    @Autowired
    public MyUserViewController(UserRepository userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/list";  // указывает на users/list.html
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new MyUser());
        return "users/form";  // указывает на users/form.html
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String createUser(@ModelAttribute MyUser user, RedirectAttributes redirectAttributes) {
        userService.save(user);
        redirectAttributes.addFlashAttribute("success", "Пользователь успешно добавлен");
        return "redirect:/user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return userService.findById(id).map(user -> {
            model.addAttribute("user", user);
            return "users/form";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("error", "Пользователь не найден");
            return "redirect:/user";
        });
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute MyUser userDetails, RedirectAttributes redirectAttributes) {
        userService.findById(id).ifPresent(user -> {
            user.setName(userDetails.getName());
            user.setPassword(userDetails.getPassword());
            user.setRole(userDetails.getRole());
            userService.save(user);
            redirectAttributes.addFlashAttribute("success", "Пользователь успешно обновлен");
        });
        return "redirect:/user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Пользователь успешно удален");
        } else {
            redirectAttributes.addFlashAttribute("error", "Пользователь не найден");
        }
        return "redirect:/user";
    }
}
