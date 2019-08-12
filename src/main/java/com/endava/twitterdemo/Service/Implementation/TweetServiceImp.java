package com.endava.twitterdemo.Service.Implementation;

import com.endava.twitterdemo.Model.Tweet;
import com.endava.twitterdemo.Repository.TweetRepository;
import com.endava.twitterdemo.Repository.UserRepository;
import com.endava.twitterdemo.Service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TweetServiceImp implements TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @Autowired
    public TweetServiceImp(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Tweet createTweet(Tweet tweet, Long userId) {
        tweet.setUser(userRepository.findById(userId));
        return tweetRepository.save(tweet);
    }

    @Override
    public Optional<Tweet> getTweetById(Long id) {
        return tweetRepository.findById(id);
    }
}
