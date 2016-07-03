package com.diferdin.tests.obfuscatedmessagestore.dao;

import com.diferdin.tests.obfuscatedmessagestore.dao.MessageDao;
import com.diferdin.tests.obfuscatedmessagestore.domain.Message;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class MessageDaoTest {

    private MessageDao messageDao;

    @Before
    public void configureTest() {
        messageDao = new MessageDao();
    }

    @Test
    public void shouldAddMessage() {
        Message message = new Message("sender", "receiver", "message");

        boolean messageAdded = messageDao.addMessage(message);

        assertTrue(messageAdded);
    }

}
