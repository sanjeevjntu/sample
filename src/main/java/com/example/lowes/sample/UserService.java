package com.example.lowes.sample;

import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
}
