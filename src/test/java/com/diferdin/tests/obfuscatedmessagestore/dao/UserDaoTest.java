package com.diferdin.tests.obfuscatedmessagestore.dao;


import com.diferdin.tests.obfuscatedmessagestore.dao.UserDao;
import com.diferdin.tests.obfuscatedmessagestore.domain.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static junit.framework.TestCase.*;

public class UserDaoTest {

    private UserDao userDao;

    @Before
    public void configureTest() {
        userDao = new UserDao();
    }

    @Test
    public void shouldAddUsers() {
        boolean jasonAddition = userDao.addUser(new User("Jason Bourne"));
        boolean janeAddition = userDao.addUser(new User("Jane Eyre"));

        assertTrue(jasonAddition);
        assertTrue(janeAddition);
        assertEquals(2, userDao.getNumberOfUsersStored());
    }

    @Test
    public void shouldNotAddDuplicateUser() {
        User jason = new User("Jason Bourne");
        boolean jasonAddition = userDao.addUser(jason);

        assertTrue(jasonAddition);
        assertEquals(1, userDao.getNumberOfUsersStored());

        boolean jasonAddition2 = userDao.addUser(jason);

        assertFalse(jasonAddition2);
        assertEquals(1, userDao.getNumberOfUsersStored());
    }

    @Test
    public void shouldGetUser() throws Exception {
        User jason = new User("Jason Bourne");
        boolean jasonAddition = userDao.addUser(jason);

        assertTrue(jasonAddition);
        assertEquals(1, userDao.getNumberOfUsersStored());

        Optional<User> retrievedJasonOptional = userDao.getUser(jason.getUuid());

        assertTrue(retrievedJasonOptional.isPresent());

        User retrievedJason = retrievedJasonOptional.get();

        assertEquals("Jason Bourne", retrievedJason.getName());
        assertEquals(jason.getUuid(), retrievedJason.getUuid());
        assertEquals(jason, retrievedJason);
    }

    @Test
    public void shouldRemoveUser() throws Exception {
        User jason = new User("Jason Bourne");
        boolean jasonAddition = userDao.addUser(jason);

        User jason2 = new User("Jason Burne");
        boolean jason2Addition = userDao.addUser(jason2);

        assertTrue(jasonAddition);
        assertTrue(jason2Addition);
        assertEquals(2, userDao.getNumberOfUsersStored());

        boolean jasonRemoval = userDao.removeUser(jason.getUuid());
        assertTrue(jasonRemoval);
        assertEquals(1, userDao.getNumberOfUsersStored());

        Optional<User> jason2Retrieved = userDao.getUser(jason2.getUuid());
        assertTrue(jason2Retrieved.isPresent());
        assertEquals(jason2Retrieved.get().getUuid(), jason2.getUuid());

        Optional<User> jasonRetrieved = userDao.getUser(jason.getUuid());
        assertFalse(jasonRetrieved.isPresent());
    }

    @Test
    public void shouldContainUser() {
        User jason = new User("Jason Bourne");
        User jack = new User("Jack Ryan");
        boolean jasonAddition = userDao.addUser(jason);

        assertTrue(jasonAddition);
        assertEquals(1, userDao.getNumberOfUsersStored());

        boolean containsJason = userDao.containsUser(jason.getUuid());
        assertTrue(containsJason);

        boolean containsJack = userDao.containsUser(jack.getUuid());
        assertFalse(containsJack);
    }

    @Test
    public void shouldRemoveAllUsers() {
        User jason = new User("Jason Bourne");
        User lara = new User("Lara Croft");
        User indiana = new User("Indiana Jones");

        boolean jasonAdded = userDao.addUser(jason);
        boolean laraAdded = userDao.addUser(lara);
        boolean indianaAdded = userDao.addUser(indiana);
        boolean jasonAddedSecond = userDao.addUser(jason);

        assertTrue(jasonAdded);
        assertTrue(laraAdded);
        assertTrue(indianaAdded);
        assertFalse(jasonAddedSecond);

        assertEquals(3, userDao.getNumberOfUsersStored());

        userDao.removeAll();

        assertEquals(0, userDao.getNumberOfUsersStored());
    }
}
