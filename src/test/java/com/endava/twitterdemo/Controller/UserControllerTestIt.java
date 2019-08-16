package com.endava.twitterdemo.Controller;

import com.endava.twitterdemo.Exceptions.UserDoesNotExistException;
import com.endava.twitterdemo.Model.User;
import com.endava.twitterdemo.Repository.UserRepository;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("it")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTestIt {

    private static final String URL_USER_SAVE = "/user";
    private static final String URL_GET_USER_BY_ID = "/user/1";
    private static final String URL_UPDATE_USER_PASSWORD = "/user/2";
    private static final String URL_DELETE_USER = "/user";
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private User user;
    private User updateUser;
    private Gson gson;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        user = new User().withEmail("kfsnaans@gmail.com").withUsername("Trajce").withPassword("password1243").withId(1L);
        updateUser = new User().withEmail("hsgaioshogiheio@gmail.com").withUsername("Dimitar").withPassword("kiko1243").withId(2L);
        userRepository.save(user);
        userRepository.save(updateUser);

    }


    @Test
    public void createUser() throws Exception {
        User newUser = new User().withEmail("bla_bla@gmail.com").withUsername("Slavche").withPassword("lllaaa");
        String jsonString = gson.toJson(newUser);
        mockMvc.perform(post(URL_USER_SAVE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

        Optional<User> userFromDb = userRepository.findById(3L);

        assertEquals(newUser.getUsername(), userFromDb.get().getUsername());
        assertEquals(newUser.getPassword(), userFromDb.get().getPassword());

    }

    @Test
    public void getUserById() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(URL_GET_USER_BY_ID)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        //System.out.println(content);
        User returnedUser = gson.fromJson(content, User.class);
        assertEquals(user.getUsername(), returnedUser.getUsername());
        assertThat(returnedUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void updateUser() throws Exception {
        mockMvc.perform(put(URL_UPDATE_USER_PASSWORD)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("password", "passwordblablabal")).andExpect(status().isOk());
        Optional<User> modifiedUser = userRepository.findById(2L);
        assertThat(user.getPassword()).isNotEqualTo(modifiedUser.get().getPassword());
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete(URL_DELETE_USER)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "2")).andExpect(status().isOk());

        assertThat(userRepository.findById(2L).isPresent()).isFalse();

    }

    @Test
    public void deleteUserIncorrect() throws Exception {

        expectedException.expect(UserDoesNotExistException.class);
        try {
            mockMvc.perform(delete(URL_DELETE_USER)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("id", "20")).andExpect(status().isOk());
        } catch (NestedServletException e) {
            if (e.getCause().getClass() == UserDoesNotExistException.class) {
                throw new UserDoesNotExistException();
            }
        }

    }

    @Test
    public void deleteUserIncorrect1() throws Exception { //not working, even Mila can't make it work

        mockMvc.perform(delete(URL_DELETE_USER)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "20"))
                .andDo(mvcResult -> {
                    String message = mvcResult.getResponse().getErrorMessage();
                    assertEquals(message, "sewrtewsrt");
                });


    }

}
