package com.example.lms_backend.service;

import com.example.lms_backend.model.Users;
import com.example.lms_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//    
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    
//    /**
//     * Authenticates the user based on username and password.
//     * 
//     * @param username the username provided by the client
//     * @param password the password provided by the client
//     * @return the authenticated User if credentials are valid; otherwise, null
//     */
//    public Users authenticate(String username, String password) {
//        Users user = userRepository.findByUsername(username);
//        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
//            return user;
//        }
//        return null;
//    }
//    
//    // Additional user-related methods (e.g., registration) can be added here.
//}


//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//    
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    
////    public Users authenticate(String username, String password) {
////        Users user = userRepository.findByUsername(username);
////        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
////            return user;
////        }
////        return null;
////    }
//}




@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    
    /**
     * Authenticates the user by username and password.
     * 
     * @param username the username from the client
     * @param password the password from the client
     * @return the authenticated User if credentials are valid, otherwise null
     */
    public Users authenticate(String username, String password) {
        Users user = userRepository.findByUsername(username);
        // Make sure passwords are stored as BCrypt hashes.
        System.out.println("User name: " + username);
        System.out.println("User found: " + user);
        //if (user != null && passwordEncoder.matches(password, user.getPassword())) {
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}

