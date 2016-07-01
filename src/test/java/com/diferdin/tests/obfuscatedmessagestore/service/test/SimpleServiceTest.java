package com.diferdin.tests.obfuscatedmessagestore.service.test;

import com.diferdin.tests.obfuscatedmessagestore.dao.UserDao;
import com.diferdin.tests.obfuscatedmessagestore.domain.User;
import com.diferdin.tests.obfuscatedmessagestore.service.SimpleUserService;
import com.diferdin.tests.obfuscatedmessagestore.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimpleServiceTest {

    @InjectMocks
    private SimpleUserService userService;

    @Spy
    private UserDao userDao;

    @Before
    public void configureTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldAddUser() {
        User jason = new User("Jason Bourne");

        boolean jasonAdded = userService.addUser(jason);

        assertTrue(jasonAdded);
        assertEquals(1, userDao.getNumberOfUsersStored());
    }

    @Test
    public void shouldGetUser() {
        User jason = new User("Jason Bourne");
        userService.addUser(jason);

        User retrieved = userService.getUser(jason.getUuid());
        assertEquals(jason, retrieved);
    }

    @Test
    public void shouldRemoveUser() {
        User jason = new User("Jason Bourne");
        userService.addUser(jason);

        boolean removed = userService.removeUser(jason.getUuid());

        assertTrue(removed);
        assertEquals(0, userDao.getNumberOfUsersStored());
    }

    @Test
    public void shouldGetAllUsers() {
        User jason = new User("Jason Bourne");
        userService.addUser(jason);

        User lara = new User("Lara Croft");
        userService.addUser(lara);

        User jack = new User("Jack Reacher");
        userService.addUser(jack);

        List<User> users = userService.getAllUsers();

        assertEquals(3, users.size());
        assertEquals(jason, users.get(0));
        assertEquals(lara, users.get(1));
        assertEquals(jack, users.get(2));
    }
}
