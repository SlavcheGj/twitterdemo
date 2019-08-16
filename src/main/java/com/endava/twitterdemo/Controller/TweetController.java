package com.endava.twitterdemo.Controller;

import com.endava.twitterdemo.Model.Tweet;
import com.endava.twitterdemo.Model.User;
import com.endava.twitterdemo.Service.TweetService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
@Profile("dev")
@RestController
@RequestMapping(value = "/")
public class TweetController {

    private final TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    //post tweet
    @PostMapping(value = "/{id}/tweet")
    public ResponseEntity<?> createTweet(@Valid @RequestBody Tweet tweet, @PathVariable(value = "id") Long userId) {
        Tweet createdTweet = tweetService.createTweet(tweet, userId);
        return new ResponseEntity<>(createdTweet, HttpStatus.CREATED);
    }

    //Get Tweet by tweet id
    @GetMapping(value = "/tweet/{id}")
    public ResponseEntity<?> getTweet(@PathVariable(value = "id") Long id) {
        Optional<Tweet> tweetById = tweetService.getTweetById(id);
        return new ResponseEntity<>(tweetById, HttpStatus.OK);

    }

    //Get Tweets By user id
    @GetMapping(value = "/all-tweets-by-user/{id}")
    public ResponseEntity<?> getTweetsByUserId(@PathVariable(value = "id") Long id) {
        Set<Tweet> tweets = tweetService.getAllTweetsByUserId(id);
        return new ResponseEntity<>(tweets, HttpStatus.OK);

    }

    //Get Tweets from user by date od creation
    @GetMapping(value = "/tweets/{id}/{dateOfCreation}")
    public ResponseEntity<?> getTweetsByUserIdOnAGivenDate(@PathVariable(value = "id") Long id, @PathVariable(value = "dateOfCreation") String date) throws ParseException {
        Set<Tweet> tweetsByUserIdAndDate = tweetService.getAllTweetsByUserIdOnGivenDay(id, date);
        return new ResponseEntity<>(tweetsByUserIdAndDate, HttpStatus.OK);

    }

    //Get all users that tweeted las month
    @GetMapping(value = "/tweets-from-last-Month")
    public ResponseEntity<?> getUsersThatTweetedLastMonth() throws ParseException {
        Set<User> usersThatTweetedLastMont = tweetService.getAllUserThatHaveTweetedLastMonth();
        return new ResponseEntity<>(usersThatTweetedLastMont, HttpStatus.OK);

    }

    @PostMapping(value = "/tweet/{id}")
    public ResponseEntity<?> updateTweet(@Valid @RequestParam String content, @PathVariable(value = "id") Long id) {
        tweetService.updateContent(id, content);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping(value = "/all-tweets-by-user/{id}")
    public ResponseEntity<?> deleteTweetsByUserId(@PathVariable(value = "id") Long id) {
        tweetService.deleteAllTweetsByUserId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
