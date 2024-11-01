package org.example.itog.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
@PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping()
    public String home() {
        return "navbar"; // Название вашего HTML файла без .html
    }
}
