package com.endava.twitterdemo.Service.Implementation;


import com.endava.twitterdemo.Model.User;
import com.endava.twitterdemo.Repository.UserRepository;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
public class UserServiceImpTest {

    @InjectMocks
    private UserServiceImp userServiceImp;


    @Mock
    private UserRepository userRepository;

    private User user;

    @Before
    public void setUp(){

        userServiceImp = new UserServiceImp(userRepository);
        user = new User()
                .withUsername("Trajce")
                .withPassword("password123")
                .withEmail("slavche_gorgiev@gmail.com");
    }

    @Test
    public void shouldCreateUser() {
        User user = new User()
                .withUsername("Slavche")
                .withPassword("password123")
                .withEmail("slavche_gorgiev@gmail.com");
        Mockito.when(userRepository.save(user)).thenReturn(user);

        User createdUser = userServiceImp.createUser(user);

        assertEquals(user.getId(),createdUser.getId());

    }

    @Test
    public void shouldGetById() {
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        User userById = userServiceImp.getById(1l).get();

        assertEquals(user.getId(),userById.getId());

    }

    @Test
    public void updatePassword() {
    Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
    Mockito.when(userRepository.save(user)).thenReturn(user);
    String newPassword = "changedPassword";
    String beforeUpdate =user.getPassword();
    User updatedUser = userServiceImp.updatePassword(1L,newPassword);
    assertNotEquals(updatedUser.getPassword(),beforeUpdate);

    }

}
