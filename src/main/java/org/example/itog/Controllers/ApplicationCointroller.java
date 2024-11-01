package org.example.itog.Controllers;

import org.example.itog.ApplicationService;
import org.example.itog.Models.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class ApplicationCointroller {
    @Autowired
    ApplicationService applicationService;
    @PostMapping("/new-user")
    public String newUser(@RequestBody MyUser user) {
        applicationService.addUser(user);
        return "success";
    }

}
