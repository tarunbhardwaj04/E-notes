package com.enotes.controller;

import com.enotes.entity.Notes;
import com.enotes.entity.User;
import com.enotes.repository.UserRepository;
import com.enotes.service.NotesService;
import com.enotes.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private NotesService notesService;
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;




    @ModelAttribute
    public User getUser(Principal p, Model m){
       String email =  p.getName();
       User user = userRepo.findByEmail(email);
       m.addAttribute("user",user);
       return user;
    }
   @PostMapping("/updateProfile")
public String updateProfile(@ModelAttribute User updatedUser, Principal principal, HttpSession session) {
    User user = userService.getUserByEmail(principal.getName());
    
    // Update all fields
    user.setName(updatedUser.getName());
    user.setQualification(updatedUser.getQualification());
    user.setGender(updatedUser.getGender());
    user.setAddress(updatedUser.getAddress());
    
    userService.saveUser(user);
    
    session.setAttribute("msg", "Profile updated successfully!");
    return "redirect:/user/profile";
}

@GetMapping("/deleteAccount/{id}")
public String deleteAccount(@PathVariable int id, HttpSession session) {
    // Note: In a real app, you might want to delete user's notes first or use CascadeType.ALL
    boolean deleted = userService.deleteUser(id);
    
    if (deleted) {
        session.setAttribute("msg", "Account deleted successfully. We are sorry to see you go!");
        return "redirect:/logout"; // Redirect to logout so session is cleared
    } else {
        session.setAttribute("errorMsg", "Something went wrong!");
        return "redirect:/user/profile";
    }
}
    



     @GetMapping("/addNotes")
    public String addNotes() {
        return "add_notes";
    }
    @GetMapping("/profile")
    public String displayProfile(Model model, Principal principal) {
    // Assuming you have a service to get user by email/username
    String email = principal.getName();
    User user = userService.getUserByEmail(email);
    model.addAttribute("user", user);
    model.addAttribute("title", "My Profile - Enotes");
    return "profile";
    }

    @GetMapping("/viewNotes")
    public String viewNotes(Model m, Principal p, @RequestParam(defaultValue = "0") Integer pageNo) {
        User user = getUser(p,m);
        Page<Notes> notes = notesService.getNotesByUser(user,pageNo);
        m.addAttribute("currentPage",pageNo);
        m.addAttribute("totalElements", notes.getTotalElements());
        m.addAttribute("totalPages",notes.getTotalPages());
        m.addAttribute("notesList",notes.getContent());
        return "view_notes";
    }



    @GetMapping("/editNotes/{id}")
    public String editNotes(@PathVariable int id, Model m) {
        Notes notes = notesService.getNotesById(id);
        m.addAttribute("n",notes);
        return "edit_notes";
    }

//    @PostMapping("/saveNotes")
//    public String saveNotes(@ModelAttribute Notes notes, HttpSession session, Principal p, Model m){
//        notes.setDate(LocalDate.now());
//
//
//        Notes saveNotes = notesService.saveNotes(notes);
//        if(saveNotes != null){
//            session.setAttribute("msg","Notes save Success");
//        } else{
//            session.setAttribute("msg","Something wrong");
//        }
//        return "redirect:/user/addNotes";
//
//    }

    @PostMapping("/saveNotes")
    public String saveNotes(@ModelAttribute Notes notes,
                            HttpSession session,
                            Principal principal) {
        try {
            // Get logged-in user
            User user = userRepo.findByEmail(principal.getName());

            // Set user and date
            notes.setUser(user);
            notes.setDate(LocalDate.now());

            // Save notes
            Notes savedNotes = notesService.saveNotes(notes);

            if (savedNotes != null) {
                session.setAttribute("msg", "Notes saved successfully");
            } else {
                session.setAttribute("msg", "Failed to save notes");
            }
        } catch (Exception e) {
            session.setAttribute("msg", "Error: " + e.getMessage());
        }

        return "redirect:/user/addNotes";
    }

    @PostMapping("/updateNotes")
    public String updateNotes(@ModelAttribute Notes notes, HttpSession session, Principal principal) {
        try {
            // Get logged-in user
            User user = userRepo.findByEmail(principal.getName());

            // Set user and date
            notes.setUser(user);
            notes.setDate(LocalDate.now());

            // Save notes
            Notes savedNotes = notesService.saveNotes(notes);

            if (savedNotes != null) {
                session.setAttribute("msg", "Notes update successfully");
            } else {
                session.setAttribute("msg", "Failed to update notes");
            }
        } catch (Exception e) {
            session.setAttribute("msg", "Error: " + e.getMessage());
        }

        return "redirect:/user/viewNotes";
    }

    @GetMapping("/deleteNotes/{id}")
    public String deleteNotes(@PathVariable int id,HttpSession session) {
        boolean f = notesService.deleteNotes(id);
        if(f){
            session.setAttribute("msg","Delete successfull");
        }else{
            session.setAttribute("msg","Something wrong");
        }
        return "redirect:/user/viewNotes";
    }

    


    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String oldPassword, 
                                @RequestParam String newPassword, 
                                Principal principal, HttpSession session) {
        
        User user = userService.getUserByEmail(principal.getName());
        
        // Check if the old password matches the database
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            // Encode and save the new password
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.saveUser(user);
            session.setAttribute("msg", "Password changed successfully!");
        } else {
            session.setAttribute("errorMsg", "Old password is incorrect!");
        }
        
        return "redirect:/user/profile";
}





}


