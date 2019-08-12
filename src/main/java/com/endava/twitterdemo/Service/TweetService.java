package com.endava.twitterdemo.Service;

import com.endava.twitterdemo.Model.Tweet;

import java.util.Optional;


public interface TweetService {
    public Tweet createTweet(Tweet tweet,Long userId);

    public Optional<Tweet> getTweetById(Long id);
}
