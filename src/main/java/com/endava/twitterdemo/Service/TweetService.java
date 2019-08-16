package com.endava.twitterdemo.Service;

import com.endava.twitterdemo.Model.Tweet;
import com.endava.twitterdemo.Model.User;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;
import java.util.Set;


public interface TweetService {
    Tweet createTweet(Tweet tweet, Long userId);

    Optional<Tweet> getTweetById(Long id);

    Set<Tweet> getAllTweetsByUserId(Long id);

    Set<Tweet> getAllTweetsByUserIdOnGivenDay(Long userId, String dateOfCreatedTweet) throws ParseException;

    Set<User> getAllUserThatHaveTweetedLastMonth() throws ParseException;

    Tweet updateContent(Long id, String content);

    Set<Tweet> deleteAllTweetsByUserId(Long id);

}
