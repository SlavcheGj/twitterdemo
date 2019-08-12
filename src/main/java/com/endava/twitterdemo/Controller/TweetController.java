package com.endava.twitterdemo.Controller;

import com.endava.twitterdemo.Model.Tweet;
import com.endava.twitterdemo.Service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(value = "/")
public class TweetController {

    private final TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping(value = "/{id}/tweet")
    public ResponseEntity<?> createTweet(@Valid @RequestBody Tweet tweet ,@PathVariable(value = "id") Long userId){
        Tweet createdTweet = tweetService.createTweet(tweet,userId);
        return new ResponseEntity<>(createdTweet,HttpStatus.CREATED);
    }

    @GetMapping(value = "/tweet/{id}")
    public ResponseEntity<?> getTweet(@PathVariable(value = "id") Long id){
        Optional<Tweet> tweetById = tweetService.getTweetById(id);
        return new ResponseEntity<>(tweetById,HttpStatus.OK);

    }

}
