package com.diferdin.tests.obfuscatedmessagestore.service;

import com.diferdin.tests.obfuscatedmessagestore.dao.UserDao;
import com.diferdin.tests.obfuscatedmessagestore.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class SimpleUserService implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageService messageService;

    @Override
    public boolean addUser(User user) {
        return !userDao.containsUser(user.getUuid()) && userDao.addUser(user);

    }

    @Override
    public User getUser(String uuid) {
        Optional<User> userOptional = userDao.getUser(uuid);
        if(!userOptional.isPresent()) {
            return null;
        }

        return userOptional.get();
    }

    @Override
    public boolean containsUser(String userId) {
        return userDao.containsUser(userId);
    }

    @Override
    public boolean removeUser(String userId) {
        messageService.removeMessagesForUser(userId);
        return userDao.removeUser(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
