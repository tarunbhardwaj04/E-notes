package com.enotes.service;

import com.enotes.entity.User;
import com.enotes.repository.UserRepository;
import jakarta.servlet.http.HttpSession;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    
    @Override
    public User saveUser(User user) {
        // Check if email already exists
        if (userRepo.existsByEmail(user.getEmail())) {
            return null; // Ya throw Custom Exception
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        user.setRegistrationDate(new Date());
        return userRepo.save(user);
    }
    @Override
    public User getUserById(int id) {
    return userRepo.findById(id).get();
    }

    @Override
public User updateUser(User user) {
    User existingUser = userRepo.findById(user.getId()).orElse(null);
    if (existingUser != null) {
        existingUser.setName(user.getName());
        existingUser.setRole(user.getRole());
        return userRepo.save(existingUser);
    }
    return null;
}

    @Override
    public boolean deleteUser(int id)
    {
            User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            userRepo.delete(user);
            return true;
        }
        return false;
    }
    
    // UserServiceImpl.java mein
    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
    
    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public boolean existEmailCheck(String email) {
        return userRepo.existsByEmail(email);
    }

    @SuppressWarnings("null")
    public void removeSessionMessages(){
       HttpSession session = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
       session.removeAttribute("msg");
    }
}
