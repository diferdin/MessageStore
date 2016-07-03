package com.diferdin.tests.obfuscatedmessagestore.service;

import com.diferdin.tests.obfuscatedmessagestore.dao.MessageDao;
import com.diferdin.tests.obfuscatedmessagestore.domain.Message;
import com.diferdin.tests.obfuscatedmessagestore.service.obfuscation.Obfuscator;
import org.springframework.beans.factory.annotation.Autowired;

public class SimpleMessageService implements MessageService {

    @Autowired
    private Obfuscator obfuscator;

    @Autowired
    private MessageDao messageDao;

    public boolean addMessage(Message message) {
        Message obfuscatedMessage = obfuscator.obfuscate(message);

        return messageDao.addMessage(obfuscatedMessage);

    }
}
