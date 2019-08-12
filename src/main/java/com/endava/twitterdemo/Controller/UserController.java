package com.endava.twitterdemo.Controller;

import com.endava.twitterdemo.Model.Tweet;
import com.endava.twitterdemo.Model.User;
import com.endava.twitterdemo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.Optional;

@RestController
@RequestMapping(value = "/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/user")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user){
        User createdUser = userService.createUser(user);
        return new ResponseEntity<User>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<?> getTweet(@PathVariable(value = "id") Long id){
        Optional<User> userById = userService.getById(id);
        return new ResponseEntity<>(userById,HttpStatus.OK);

    }

}
