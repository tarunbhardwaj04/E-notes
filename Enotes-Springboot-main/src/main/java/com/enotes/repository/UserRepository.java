package com.enotes.repository;

import com.enotes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    public boolean existsByEmail(String email);

//    for security
    public User findByEmail(String email);
    

}
