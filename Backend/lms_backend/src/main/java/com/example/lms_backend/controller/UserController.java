package com.example.lms_backend.controller;

import com.example.lms_backend.model.LoginResponse;
import com.example.lms_backend.model.Users;
import com.example.lms_backend.service.UserService;
import com.example.lms_backend.Repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
 

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    

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
    	 System.out.println("----------------------------   logout : ");
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }
    
    
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestParam String username, 
                                    @RequestParam String password,
                                    HttpSession session) {
//        // Check if the username already exists
//        if (userRepository.findByUsername(username).isPresent()) {
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", false);
//            response.put("message", "Username already exists");
//            return ResponseEntity.badRequest().body(response);
//        }
        
        // Create and save the new user
        Users newUser = new Users();
        newUser.setUsername(username);
        newUser.setPassword(password); // encode the password
        newUser.setRole("nonAdmin"); 
        newUser.setContact(12455656); 
        
        Users savedUser = userRepository.save(newUser);
        
        System.out.println("----------------------------   savedUser: " + username);
        
        // Optionally, set the user in the session
        session.setAttribute("user", savedUser);
        
        // Build response
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("username", savedUser.getUsername());
        response.put("userid", savedUser.getId());
        return ResponseEntity.ok(response);
    }
}
