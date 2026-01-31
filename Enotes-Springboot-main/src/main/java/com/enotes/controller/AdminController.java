package com.enotes.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enotes.entity.User;
import com.enotes.service.UserService;

import jakarta.servlet.http.HttpSession;
  @Controller
@RequestMapping("/admin")
public class AdminController {
  


    @Autowired
    private UserService userService; 

    @ModelAttribute
    public void getUser(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            User user = userService.getUserByEmail(email);
            m.addAttribute("user", user);
        }
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model m) {
        List<User> allUsers = userService.getAllUsers();
        m.addAttribute("userList", allUsers);
        return "admin/dashboard";
    }
    @GetMapping("/editUser/{id}")
    public String editUser(@PathVariable int id, Model m) {
    // Database se user ki details nikalein
    User user = userService.getUserById(id); // Make sure ye method aapke service mein ho
    m.addAttribute("userData", user);
    return "admin/edit_user"; // Yeh aapki HTML file ka naam hoga
    }

    @GetMapping("/deleteUser/{id}")
        public String deleteUser(@PathVariable int id, HttpSession session) {
            boolean f = userService.deleteUser(id);
            if (f) {
                session.setAttribute("msg", "User deleted successfully!");
            } else {
                session.setAttribute("msg", "Something went wrong on server!");
            }
            return "redirect:/admin/dashboard";
        }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("userData") User user, HttpSession session) {
        // Yahan humne @ModelAttribute ka naam "userData" kar diya hai 
        // taaki ye controller level wale "user" se na takraye.

        User existingUser = userService.getUserById(user.getId());

        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setRole(user.getRole());

            User savedUser = userService.updateUser(existingUser);
            
            if (savedUser != null) {
                session.setAttribute("msg", "User updated successfully!");
            } else {
                session.setAttribute("msg", "Update failed!");
            }
        } else {
            session.setAttribute("msg", "User not found!");
        }
        
        return "redirect:/admin/dashboard";
    }

    
}
