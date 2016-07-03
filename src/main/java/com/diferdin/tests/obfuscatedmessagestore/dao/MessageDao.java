package com.diferdin.tests.obfuscatedmessagestore.dao;

import com.diferdin.tests.obfuscatedmessagestore.domain.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MessageDao {
    private List<Message> messages = null;

    public boolean addMessage(Message message) {
        if(messages == null) {
            messages = new ArrayList<>();
        }

        return !containsMessage(message) && messages.add(message);
    }

    public int getNumberOfMessagesStored() {
        if(messages == null) {
            return 0;
        }

        return messages.size();
    }

    public List<Message> getMessagesForRecipientId(String uuid) {
        return messages.stream().filter(m -> m.getReceiver().equals(uuid)).collect(Collectors.toList());
    }

    public boolean containsMessage(Message message) {
        return messages.stream().anyMatch(m -> m.equals(message));
    }

    public boolean removeMessagesForUser(String userId) {
        return messages.removeIf(m -> m.getReceiver().equals(userId)) || messages.removeIf(m -> m.getSender().equals(userId));
    }
}
