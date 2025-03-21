//package com.example.lms_backend.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.lms_backend.Repository.UserRepository;
//import com.example.lms_backend.model.Users;
//import com.example.lms_backend.service.UserService;
//
//import jakarta.servlet.http.HttpSession;

//@RestController
//@RequestMapping("/auth")
//public class UserController {
//    @Autowired
//    private UserRepository userRepository;
//
//    @PostMapping("/login")
//    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
//        Users user = userRepository.findByUsername(username);
//        if (user != null && user.getPassword().equals(password)) {
//            session.setAttribute("user", user);
//            return "Login successful";
//        }
//        return "Invalid username or password";
//    }
//}

// 
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//@RequestMapping("/auth")
//public class UserController {
//    
//    // Display login page
//    @GetMapping("/login")
//    public String showLoginPage() {
//        return "login";
//    }
//    
//    // Existing login POST method remains the same.
//    @PostMapping("/login")
//    public String login(@RequestParam String username, 
//                        @RequestParam String password, 
//                        HttpSession session) {
//        Users user = userRepository.findByUsername(username);
//        if (user != null && user.getPassword().equals(password)) {
//            session.setAttribute("user", user);
//            // Redirect to the books search page after successful login.
//            return "redirect:/books/search";
//        }
//        // Optionally, add an error attribute to show a message on the login page.
//        return "login";
//    }
//    
//    @PostMapping("/logout")
//    public String logout(HttpSession session) {
//        session.invalidate();
//        return "redirect:/auth/login";
//    }
//    
//    // Inject the UserRepository as before.
//    @Autowired
//    private UserRepository userRepository;
//}
//
//@RestController
//@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:3000") // allow requests from React dev server
//public class UserController {
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private UserService userService;
//    
////    @PostMapping("/login")
////    public ResponseEntity<String> login(@RequestParam String username,
////                                        @RequestParam String password,
////                                        HttpSession session) {
////        Users user = userRepository.findByUsername(username);
////        if (user != null && user.getPassword().equals(password)) {
////            session.setAttribute("user", user);
////            return ResponseEntity.ok("Login successful");
////        }
////        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
////    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(HttpSession session) {
//        session.invalidate();
//        return ResponseEntity.ok("Logged out successfully");
//    }
//    
//
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestParam String username,
//                                        @RequestParam String password,
//                                        HttpSession session) {
//        Users user = userService.authenticate(username, password);
//        if (user != null) {
//            session.setAttribute("user", user);
//            return ResponseEntity.ok("Login successful");
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//    }
//    
//}

//package com.example.lms_backend.controller;
//
//import com.example.lms_backend.model.User;
//import com.example.lms_backend.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;

//import javax.servlet.http.HttpSession;
//
//@RestController
//@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:3000")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//    
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestParam String username,
//                                        @RequestParam String password,
//                                        HttpSession session) {
//        System.out.println("Login attempt: " + username);
//        Users user = userService.authenticate(username, password);
//        if (user != null) {
//            session.setAttribute("user", user);
//            return ResponseEntity.ok("Login successful");
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//    }
//
//    
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(HttpSession session) {
//        session.invalidate();
//        return ResponseEntity.ok("Logged out successfully");
//    }
//}
//
package com.example.lms_backend.controller;

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
    public ResponseEntity<String> login(@RequestParam String username,
                                        @RequestParam String password,
                                        HttpSession session) {
        System.out.println("Login attempt: " + username);
        System.out.println("password " + password);
        Users user = userService.authenticate(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }
}

