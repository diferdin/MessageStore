package com.diferdin.tests.obfuscatedmessagestore.dao;

import com.diferdin.tests.obfuscatedmessagestore.domain.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserDao {

    private Set<User> users = null;

    public boolean addUser(User user) {
        if (users == null) {
            users = new HashSet<>();
        }

        return !containsUser(user.getUuid()) && users.add(user);

    }

    public boolean removeUser(String userId) {
        return users.removeIf(u -> u.getUuid().equals(userId));
    }

    public void removeAll() {
        if(users != null) {
            users.clear();
        }
    }

    public Optional<User> getUser(String userId) {
        List<User> matchingUsers = users.stream().filter(u -> u.getUuid().equals(userId))
                .collect(Collectors.toList());

        if(matchingUsers.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(matchingUsers.get(0));

    }

    public int getNumberOfUsersStored() {
        if(users == null) {
            return 0;
        }
        return users.size();
    }

    public boolean containsUser(String userId) {

        return users != null && users.stream().anyMatch(u -> u.getUuid().equals(userId));

    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}
