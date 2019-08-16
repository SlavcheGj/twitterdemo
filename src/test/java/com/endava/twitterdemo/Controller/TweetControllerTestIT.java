package com.endava.twitterdemo.Controller;

import com.endava.twitterdemo.Model.Tweet;
import com.endava.twitterdemo.Model.User;
import com.endava.twitterdemo.Repository.TweetRepository;
import com.endava.twitterdemo.Repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.*;


import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("it")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TweetControllerTestIT {

    private static final String URL_TWEET_SAVE = "/1/tweet";
    private static final String URL_GET_TWEET_BY_ID = "/tweet/4";
    private static final String URL_GET_ALL_TWEETS_FOR_USER = "/all-tweets-by-user/2";
    private static final String URL_GET_ALL_TWEETS_FROM_USER_ON_GIVEN_DATE = "/tweets/2/2019-07-22";
    private static final String URL_GET_USERS_WHO_TWEETED_LAST_MONTH = "/tweets-from-last-Month";
    private static final String URL_UPDATE_TWEET ="/tweet/4";
    private static final String URL_DELETE_ALL_TWEETS_BY_USER = "/all-tweets-by-user/1";
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private User constantUser;
    private Tweet tweet;
    private Tweet constantTweet;
    private Tweet constantTweet2;

    private Gson gson;


    @Before
    public void setUp() throws Exception {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        user = new User().withId(1L).withEmail("kfsnaans@gmail.com").withUsername("Trajce").withPassword("password1243");
        userRepository.save(user);
        constantUser = new User().withId(2L).withEmail("fasdgseges@gmail.com").withUsername("Slavche").withPassword("password1243");
        userRepository.save(constantUser);
        Tweet deletableTweet = new Tweet("PLEASEE DELETE MEEEE!!!!!", "2019-02-12").withId(6L).withUser(user);
        tweetRepository.save(deletableTweet);

        tweet = new Tweet("bla bla bla", "2019-01-12").withId(3L);
        constantTweet = new Tweet("bla bla bla.fusdbse", "2019-07-22").withId(4L).withUser(constantUser);
        constantTweet2 = new Tweet("bla bldasdasusdbse", "2019-02-22").withId(5L).withUser(constantUser);
        tweetRepository.save(constantTweet);
        tweetRepository.save(constantTweet2);


    }

    @Test
    public void createTweet() throws Exception {

        String jsonString = gson.toJson(tweet);
        MvcResult mvcResult = mockMvc.perform(post(URL_TWEET_SAVE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Tweet returnedTweet = gson.fromJson(content, Tweet.class);
        assertEquals(tweet.getContent(), returnedTweet.getContent());
        assertEquals(tweet.getId(), returnedTweet.getId());
    }

    @Test
    public void getTweet() throws Exception {


        MvcResult mvcResult = mockMvc.perform(get(URL_GET_TWEET_BY_ID)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Tweet returnedTweet = gson.fromJson(content, Tweet.class);
        assertEquals(constantTweet.getContent(), returnedTweet.getContent());
        assertThat(returnedTweet.getDateOfCreation()).isEqualTo(constantTweet.getDateOfCreation());
    }

    @Test
    public void getTweetsByUserId() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(URL_GET_ALL_TWEETS_FOR_USER)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        String content =  mvcResult.getResponse().getContentAsString();

        Type founderSetType = new TypeToken<HashSet<Tweet>>(){}.getType();

        Set<Tweet> returnedTweets = gson.fromJson(content, founderSetType);

        assertThat(returnedTweets.size()).isEqualTo(2);





    }

    @Test
    public void getTweetsByUserIdOnAGivenDate() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(URL_GET_ALL_TWEETS_FROM_USER_ON_GIVEN_DATE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        String content =  mvcResult.getResponse().getContentAsString();

        Type founderSetType = new TypeToken<HashSet<Tweet>>(){}.getType();

        Set<Tweet> returnedTweets = gson.fromJson(content, founderSetType);

        assertThat(returnedTweets.size()).isEqualTo(1);

    }

    @Test
    public void getAllUsersThatTweetedLastMonth() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(URL_GET_USERS_WHO_TWEETED_LAST_MONTH)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        String content =  mvcResult.getResponse().getContentAsString();

        Type founderSetType = new TypeToken<HashSet<User>>(){}.getType();

        Set<User> returnedUsers = gson.fromJson(content, founderSetType);

        assertThat(returnedUsers.size()).isEqualTo(1);


    }

    @Test
    public void updateTweet() throws Exception {
        mockMvc.perform(post(URL_UPDATE_TWEET)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("content","bla bla updated")).andExpect(status().isOk());
        Optional<Tweet> modifiedTweet = tweetRepository.findById(4L);
        assertThat(modifiedTweet.get().getContent()).isNotEqualTo(constantTweet.getContent());
    }

    @Test
    public void deleteTweetsByUserId() throws Exception {
        mockMvc.perform(delete(URL_DELETE_ALL_TWEETS_BY_USER))
                .andExpect(status().isOk());

        assertThat(tweetRepository.findAllByUserId(1L).isEmpty()).isTrue();

    }
}
