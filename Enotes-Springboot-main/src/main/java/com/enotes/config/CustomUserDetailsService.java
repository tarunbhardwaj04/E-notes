package com.enotes.config;

import com.enotes.entity.User;
import com.enotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        User user = userRepo.findByEmail(username);
//
//        if(user == null){
//            throw new UsernameNotFoundException("User not found");
//        }
//        else{
//            return new CustomUser(user);
//        }
//    }

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            User user = userRepo.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            
            // Database se user ka actual role lein (e.g., "ROLE_ADMIN" ya "ROLE_USER")
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority(user.getRole())) // ← Ab ye dynamic hai!
            );
        }
}
