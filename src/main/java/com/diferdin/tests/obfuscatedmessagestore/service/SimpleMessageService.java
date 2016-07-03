package com.diferdin.tests.obfuscatedmessagestore.service;

import com.diferdin.tests.obfuscatedmessagestore.dao.MessageDao;
import com.diferdin.tests.obfuscatedmessagestore.domain.Message;
import com.diferdin.tests.obfuscatedmessagestore.service.obfuscation.Obfuscator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class SimpleMessageService implements MessageService {

    @Autowired
    private Obfuscator obfuscator;

    @Autowired
    private MessageDao messageDao;

    public boolean addMessage(Message message) {
        Message obfuscatedMessage = obfuscator.obfuscate(message);

        return messageDao.addMessage(obfuscatedMessage);

    }

    public List<Message> getAllMessagesForReceiverId(String receiverId) {

            return messageDao.getMessagesForRecipientId(receiverId)
                    .stream()
                    .map(m -> obfuscator.clarify(m)).collect(Collectors.toList());
    }

    public boolean removeMessagesForUser(String userId) {
        return messageDao.removeMessagesForUser(userId);
    }
}
