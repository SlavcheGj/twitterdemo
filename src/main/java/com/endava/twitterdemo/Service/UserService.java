package com.endava.twitterdemo.Service;

import com.endava.twitterdemo.Model.Tweet;
import com.endava.twitterdemo.Model.User;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    User createUser(User user);

    Optional<User> getById(Long id);
    User updatePassword(Long id,String password);

    void deleteUser(Long id);


}
