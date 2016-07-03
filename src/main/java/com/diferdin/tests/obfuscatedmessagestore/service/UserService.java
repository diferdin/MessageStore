package com.diferdin.tests.obfuscatedmessagestore.service;

import com.diferdin.tests.obfuscatedmessagestore.domain.User;

import java.util.List;

public interface UserService {

    boolean addUser(User user);

    User getUser(String uuid);

    boolean removeUser(String uuid);

    boolean containsUser(String userid);

    List<User> getAllUsers();
}
