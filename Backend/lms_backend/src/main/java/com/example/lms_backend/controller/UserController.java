package com.example.lms_backend.controller;

import com.example.lms_backend.model.LoginResponse;
import com.example.lms_backend.model.Users;
import com.example.lms_backend.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password,
                                   HttpSession session) {
        Users user = userService.authenticate(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            // Return a JSON response with username
            //return ResponseEntity.ok(new LoginResponse(true, "Login successful",user.getId(), user.getUsername()));
            return ResponseEntity.ok(new LoginResponse(true, "Login successful", user.getId(), user.getUsername()));
            
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(new LoginResponse(false, "Invalid username or password",null, null));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }
}
