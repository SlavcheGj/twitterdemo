package com.endava.twitterdemo.Service;

import com.endava.twitterdemo.Exceptions.UserDoesNotExistException;
import com.endava.twitterdemo.Model.User;

import java.util.Optional;

public interface UserService {
    User createUser(User user);

    Optional<User> getById(Long id);

    User updatePassword(Long id, String password);

    void deleteUser(Long id) throws UserDoesNotExistException;


}
