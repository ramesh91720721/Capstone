package com.example.lms_backend.model;

 

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        // returns the login page template (login.html)
        return "login";
    }
}
