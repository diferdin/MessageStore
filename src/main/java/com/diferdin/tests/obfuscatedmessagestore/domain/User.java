package com.diferdin.tests.obfuscatedmessagestore.domain;

import com.fasterxml.jackson.annotation.JsonFilter;

import java.util.UUID;

@JsonFilter("userFilter")
public class User {
    private String name;
    private UUID uuid;

    public User(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public User() {
        this.uuid = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid.toString();
    }

    @Override
    public String toString() {

        return  "Name: "+name+", UUID: "+uuid;
    }

    @Override
    public boolean equals(Object object) {
        if(object == null) {
            return false;
        }

        if(!User.class.isAssignableFrom(object.getClass())) {
            return false;
        }

        final User user = (User)object;

        return name.equals(user.getName()) &&
                uuid.toString().equals(user.getUuid());
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
