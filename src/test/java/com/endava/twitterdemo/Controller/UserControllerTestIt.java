package com.endava.twitterdemo.Controller;

import com.endava.twitterdemo.Model.User;
import com.endava.twitterdemo.Repository.UserRepository;
import com.google.gson.Gson;
import org.graalvm.compiler.lir.LIRInstruction;
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

import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTestIt {

    private static final String URL_USER_SAVE = "/user";
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    private Gson gson;
    @Before
    public void setUp() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void createUser() throws Exception {

        User user = new User().withEmail("kfsnaans@gmail.com").withUsername("Trajce").withPassword("password1243");
        String jsonString = gson.toJson(user);
        mockMvc.perform(post(URL_USER_SAVE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

        Optional<User> userFromDb = userRepository.findById(1L);

        assertEquals(user.getUsername(),userFromDb.get().getUsername());
        assertEquals(user.getPassword(),userFromDb.get().getPassword());

    }

    @Test
    public void getUserById() throws Exception {
        User user = new User().withEmail("kfsnaans@gmail.com").withUsername("Trajce").withPassword("password1243");
        String jsonString = gson.toJson(user);
        mockMvc.perform(post(URL_USER_SAVE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void deleteUser() {
    }
}
