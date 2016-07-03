package com.diferdin.tests.obfuscatedmessagestore.dao;

import com.diferdin.tests.obfuscatedmessagestore.domain.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MessageDao {
    List<Message> messages = null;

    public boolean addMessage(Message message) {
        if(messages == null) {
            messages = new ArrayList<>();
        }

        return !containsMessage(message) && messages.add(message);
    }

    public List<Message> getMessagesForRecipient(String uuid) {
        return messages.stream().filter(m -> m.getReceiver().equals(uuid)).collect(Collectors.toList());
    }

    private boolean containsMessage(Message message) {
        return messages.stream().anyMatch(m -> m.equals(message));
    }
}
