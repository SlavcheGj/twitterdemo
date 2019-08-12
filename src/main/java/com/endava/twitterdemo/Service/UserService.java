package com.endava.twitterdemo.Service;

import com.endava.twitterdemo.Model.User;

import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getById(Long id);

}
