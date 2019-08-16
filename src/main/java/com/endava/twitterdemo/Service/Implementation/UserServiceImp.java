package com.endava.twitterdemo.Service.Implementation;

import com.endava.twitterdemo.Exceptions.UserDoesNotExistException;
import com.endava.twitterdemo.Model.User;
import com.endava.twitterdemo.Repository.UserRepository;
import com.endava.twitterdemo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    @Value("${spring.user.password}")
    private String defaultUserPassword;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {

        if (user.getPassword().equals("")) {
            user.setPassword(defaultUserPassword);
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updatePassword(Long id, String password) {
        Optional<User> user = userRepository.findById(id);
        user.get().setPassword(password);
        return userRepository.save(user.get());
    }

    @Override
    public void deleteUser(Long id) throws UserDoesNotExistException {
        Optional<User> isHeHere = userRepository.findById(id);
        if (!isHeHere.isPresent()) {
            throw new UserDoesNotExistException("User does not exist");
        }
        userRepository.deleteById(id);
    }


}
