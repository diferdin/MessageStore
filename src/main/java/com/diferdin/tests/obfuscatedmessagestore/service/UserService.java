package com.diferdin.tests.obfuscatedmessagestore.service;

import com.diferdin.tests.obfuscatedmessagestore.domain.User;

import java.util.List;

/**
 * Created by antonio on 01/07/2016.
 */
public interface UserService {

    boolean addUser(User user);

    User getUser(String uuid);

    boolean removeUser(String uuid);

    List<User> getAllUsers();
}
