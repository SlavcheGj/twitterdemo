package com.endava.twitterdemo.Controller;

import com.endava.twitterdemo.Model.Tweet;
import com.endava.twitterdemo.Model.User;
import com.endava.twitterdemo.Repository.TweetRepository;
import com.endava.twitterdemo.Repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MvcResult;
import static org.assertj.core.api.Assertions.*;


import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TweetControllerTestIt {

    private static final String URL_TWEET_SAVE = "/1/tweet";
    private static  final String URL_GET_TWEET_BY_ID = "/tweet/2";

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Tweet tweet;

    private Gson gson;


    @Before
    public void setUp() throws Exception {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        user = new User().withId(1L).withEmail("kfsnaans@gmail.com").withUsername("Trajce").withPassword("password1243");
        userRepository.save(user);
        tweet = new Tweet("bla bla bla","2019-07-12").withId(2L);
        tweet.setUser(userRepository.findById(1L));
        tweetRepository.save(tweet);
    }

    @Test
    public void createTweet() throws Exception {

        Tweet newTweet = new Tweet("bla bla bla","2019-07-12").withId(3L);
        String jsonString = gson.toJson(newTweet);
       MvcResult mvcResult = mockMvc.perform(post(URL_TWEET_SAVE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

       String content = mvcResult.getResponse().getContentAsString();

        Tweet returnedTweet = gson.fromJson(content, Tweet.class);
        assertEquals(newTweet.getContent(),returnedTweet.getContent());
        assertEquals(newTweet.getId(),returnedTweet.getId());
    }

    @Test
    public void getTweet() throws Exception {


        MvcResult mvcResult = mockMvc.perform(get(URL_GET_TWEET_BY_ID)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
       System.out.println(content);
        Tweet returnedTweet = gson.fromJson(content, Tweet.class);
        assertEquals(tweet.getContent(), returnedTweet.getContent());
        assertThat(returnedTweet.getDateOfCreation()).isEqualTo(tweet.getDateOfCreation());
    }

    @Test
    public void getTweetsByUserId() {
    }

    @Test
    public void getTweetsByUserId1() {
    }

    @Test
    public void getUsersThatTweetedLastMonth() {
    }

    @Test
    public void updateTweet() {
    }

    @Test
    public void deleteTweetsByUserId() {
    }
}
