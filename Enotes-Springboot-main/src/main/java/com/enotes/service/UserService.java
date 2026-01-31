package com.enotes.service;

import java.util.List;

import com.enotes.entity.User;

public interface UserService {
    public User saveUser(User user);

    public boolean existEmailCheck(String email);

    public User getUserByEmail(String email);
    public boolean deleteUser(int id);
    List<User> getAllUsers();
    public User getUserById(int id);
    public User updateUser(User user);
}
