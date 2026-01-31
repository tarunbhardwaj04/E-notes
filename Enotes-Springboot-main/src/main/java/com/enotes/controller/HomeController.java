package com.enotes.controller;

import com.enotes.entity.User;
import com.enotes.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, HttpSession session) {
        
        // 1. Pehle check karein ki email database mein hai ya nahi
        boolean emailExists = userService.existEmailCheck(user.getEmail());

        if (emailExists) {
            // Agar email pehle se hai, toh redirect karein register page par message ke saath
            session.setAttribute("msg", "User with this email already exists!");
            return "redirect:/register";
        }

        // 2. Agar email naya hai, toh save karein
        User u = userService.saveUser(user);
        
        if (u != null) {
            session.setAttribute("msg", "Registered successfully! Please login.");
            // Professional tarika registration ke baad login page par bhejna hai
            return "redirect:/signin"; 
        } else {
            session.setAttribute("msg", "Something went wrong on server!");
            return "redirect:/register";
        }
    }


    @GetMapping("/signin")
    public String login() {
        return "login";
    }



}
