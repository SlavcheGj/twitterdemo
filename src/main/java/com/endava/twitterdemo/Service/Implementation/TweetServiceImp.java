package com.endava.twitterdemo.Service.Implementation;

import com.endava.twitterdemo.Model.Tweet;
import com.endava.twitterdemo.Model.User;
import com.endava.twitterdemo.Repository.TweetRepository;
import com.endava.twitterdemo.Repository.UserRepository;
import com.endava.twitterdemo.Service.TweetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public Set<Tweet> getAllTweetsByUserId(Long id) {
        return tweetRepository.findAllByUserId(id);
    }

    @Override
    public Set<Tweet> getAllTweetsByUserIdOnGivenDay(Long userId, String dateOfCreatedTweet) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return tweetRepository.findAllByDateOfCreationAndUserId(formatter.parse(dateOfCreatedTweet),userId);
    }
    @Transactional
    @Override
    public Set<User> getAllUserThatHaveTweetedLastMonth() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate beforeDate = LocalDate.now();
        beforeDate=beforeDate.withDayOfMonth(1);
        LocalDate date = LocalDate.now();
        LocalDate afterDate;
        afterDate=date.minusMonths(2);
        int numbertOfDays = afterDate.lengthOfMonth();
        afterDate = afterDate.withDayOfMonth(numbertOfDays);

        Set<User>  userTweetedLastMonth=tweetRepository.findAllUserByDateOfCreationBetween(formatter.parse(afterDate.toString()),formatter.parse(beforeDate.toString()))
                .stream().map(tweet -> tweet.getUser()).collect(Collectors.toSet());

        return userTweetedLastMonth;
    }

    @Override
    public void updateContent(Long id, String content) {
        Optional<Tweet> tweet = tweetRepository.findById(id);
        tweet.get().setContent(content);
        tweetRepository.save(tweet.get());

    }

    @Override
    public void deleteAllTweetsByUserId(Long id) {
        tweetRepository.deleteAllByUserId(id);
    }


}
