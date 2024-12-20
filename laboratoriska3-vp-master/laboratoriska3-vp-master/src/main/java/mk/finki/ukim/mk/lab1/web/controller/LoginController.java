package mk.finki.ukim.mk.lab1.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login"; // "login.html" треба да постои во resources/templates
    }
}
