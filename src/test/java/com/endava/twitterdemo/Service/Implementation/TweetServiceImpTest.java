package com.endava.twitterdemo.Service.Implementation;

import com.endava.twitterdemo.Model.Tweet;
import com.endava.twitterdemo.Model.User;
import com.endava.twitterdemo.Repository.TweetRepository;
import com.endava.twitterdemo.Repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Matchers.any;

@RunWith(SpringRunner.class)
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

        assertThat(createdTweet.getContent()).isEqualTo(tweet.getContent());
        assertThat(createdTweet.getDateOfCreation()).isEqualTo(tweet.getDateOfCreation());
    }

    @Test
    public void shouldGetTweetById() {
        Mockito.when(tweetRepository.findById(2L)).thenReturn(java.util.Optional.of(originalTweet));
        Optional<Tweet> foundTweet = tweetServiceImp.getTweetById(2L);

        assertThat(foundTweet.get().getContent()).isEqualTo(originalTweet.getContent());
        assertThat(foundTweet.get().getDateOfCreation()).isEqualTo(originalTweet.getDateOfCreation());
    }

    @Test
    public void shouldGetAllTweetsByUserId() throws ParseException {
        Set<Tweet> tweets = new HashSet<>();
        tweets.add(originalTweet);
        tweets.add(new Tweet("INLINE BOIS", "2019-05-05"));
        Mockito.when(tweetRepository.findAllByUserId(1L)).thenReturn(new HashSet<>(tweets));
        Set<Tweet> foundTweets = tweetServiceImp.getAllTweetsByUserId(1L);

        assertThat(foundTweets).isEqualTo(tweets);
    }

    @Test
    public void getAllTweetsByUserIdOnGivenDay() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Set<Tweet> tweets = new HashSet<>();
        tweets.add(originalTweet);
        Mockito.when(tweetRepository.findAllByDateOfCreationAndUserId(formatter.parse("2019-05-06"), 1L)).thenReturn(new HashSet<>(tweets));
        Set<Tweet> foundTweets = tweetServiceImp.getAllTweetsByUserIdOnGivenDay(1L, "2019-05-06");

        assertThat(foundTweets).isEqualTo(tweets);
    }

    @Test
    public void getAllUserThatHaveTweetedLastMonth() throws ParseException {
        Set<Tweet> tweets = new HashSet<>();
        tweets.add(new Tweet("INLINE BOIS", "2019-05-05").withUser(originalUser));
        Mockito.when(tweetRepository.findAllUserByDateOfCreationBetween(any(Date.class), any(Date.class))).thenReturn(new HashSet<>(tweets));

        Set<User> expectedUsers = new HashSet<>();
        expectedUsers.add(originalUser);
        Set<User> foundUsers = tweetServiceImp.getAllUserThatHaveTweetedLastMonth();

        assertThat(foundUsers).isEqualTo(expectedUsers);
    }

    @Test
    public void updateContent() {
        Tweet workingTweet = new Tweet(originalTweet);
        Mockito.when(tweetRepository.findById(2L)).thenReturn(Optional.of(workingTweet));
        Mockito.when(tweetRepository.save(workingTweet)).thenReturn(workingTweet);

        Tweet updatedTweet = tweetServiceImp.updateContent(2L, "LOLLOLOL");

        assertThat(updatedTweet.getContent()).isNotEqualTo(originalTweet.getContent());
    }

    @Test
    public void deleteAllTweetsByUserId() throws ParseException {
        Set<Tweet> tweets = new HashSet<>();
        tweets.add(originalTweet);
        tweets.add(new Tweet("INLINE BOIS", "2019-05-05").withUser(originalUser));

        Mockito.when(tweetRepository.findAllByUserId(1L)).thenReturn(new HashSet<>(tweets));

        Set<Tweet> deletedTweets = tweetServiceImp.deleteAllTweetsByUserId(1L);

        assertThat(deletedTweets).isEqualTo(tweets);
    }
}
