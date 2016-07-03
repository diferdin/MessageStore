package com.diferdin.tests.obfuscatedmessagestore.domain;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class UserTest {

    @Test
    public void shouldGetFields() {
        User jason = new User("Jason Bourne");

        assertEquals("Jason Bourne", jason.getName());
        assertNotNull(jason.getUuid());
    }

    @Test
    public void shouldNotEqualUsers() {
        User jason = new User("Jason Bourne");
        User lara = new User("Lara Croft");

        assertFalse(jason.equals(lara));
    }
}
