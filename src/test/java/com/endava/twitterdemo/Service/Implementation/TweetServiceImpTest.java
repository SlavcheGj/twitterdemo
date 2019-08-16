package com.endava.twitterdemo.Service.Implementation;

import com.endava.twitterdemo.Model.Tweet;
import com.endava.twitterdemo.Model.User;
import com.endava.twitterdemo.Repository.TweetRepository;
import com.endava.twitterdemo.Repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.text.ParseException;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;


public class TweetServiceImpTest {

    @InjectMocks
    private TweetServiceImp tweetServiceImp;

    @InjectMocks
    private UserServiceImp userServiceImp;


    @Mock
    private UserRepository userRepository;

    @Mock
    private TweetRepository tweetRepository;

    private User originalUser;
    private Tweet originalTweet;

    @Before
    public void setUp() throws ParseException {

        userServiceImp = new UserServiceImp(userRepository);
        originalUser = new User()
                .withUsername("Trajce")
                .withPassword("password123")
                .withEmail("slavche_gorgiev@gmail.com");

        originalTweet = new Tweet("Hello", "2019-07-22").withUser(originalUser);
    }

    @Test
    public void shouldCreateTweet() throws ParseException {
        Tweet tweet = new Tweet("CREATED", "2019-08-16");
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(originalUser));
        Mockito.when(tweetRepository.save(tweet)).thenReturn(tweet);

        Tweet createdTweet = tweetServiceImp.createTweet(tweet, 1L);

        assertThat(createdTweet);
    }

    @Test
    public void getTweetById() {
    }

    @Test
    public void getAllTweetsByUserId() {
    }

    @Test
    public void getAllTweetsByUserIdOnGivenDay() {
    }

    @Test
    public void getAllUserThatHaveTweetedLastMonth() {
    }

    @Test
    public void updateContent() {
    }

    @Test
    public void deleteAllTweetsByUserId() {
    }
}
