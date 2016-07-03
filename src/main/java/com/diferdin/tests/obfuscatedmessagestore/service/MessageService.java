package com.diferdin.tests.obfuscatedmessagestore.service;

import com.diferdin.tests.obfuscatedmessagestore.domain.Message;
import com.diferdin.tests.obfuscatedmessagestore.domain.User;

import java.util.List;

public interface MessageService {

    boolean addMessage(Message message);

    List<Message> getAllMessagesForReceiverId(String receiverId);

    boolean removeMessagesForUser(String userId);
}
