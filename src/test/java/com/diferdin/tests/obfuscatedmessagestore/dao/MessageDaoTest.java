package com.diferdin.tests.obfuscatedmessagestore.dao;

import com.diferdin.tests.obfuscatedmessagestore.domain.Message;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
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

    @Test
    public void shouldReturnNumberOfMessagesStored() {
        Message firstMessage = new Message("sender", "receiver", "message");
        Message secondMessage = new Message("sender", "receiver", "message2");
        Message thirdMessage = new Message("sender", "receiver", "message3");

        boolean firstAdded = messageDao.addMessage(firstMessage);
        boolean secondAdded = messageDao.addMessage(secondMessage);
        boolean thirdAdded = messageDao.addMessage(thirdMessage);

        assertTrue(firstAdded);
        assertTrue(secondAdded);
        assertTrue(thirdAdded);

        assertEquals(3, messageDao.getNumberOfMessagesStored());
    }

    @Test
    public void shouldNotAddSecondMessage() {
        Message firstMessage = new Message("sender", "receiver", "message");
        Message secondMessage = new Message("sender", "receiver", "message");

        boolean firstAdded = messageDao.addMessage(firstMessage);
        boolean secondAdded = messageDao.addMessage(secondMessage);

        assertTrue(firstAdded);
        assertFalse(secondAdded);

        assertEquals(1, messageDao.getNumberOfMessagesStored());
    }

    @Test
    public void shouldContainMessage() {
        Message firstMessage = new Message("sender", "receiver", "message");
        Message secondMessage = new Message("sender", "receiver", "message");

        messageDao.addMessage(firstMessage);

        assertTrue(messageDao.containsMessage(secondMessage));
    }

    @Test
    public void shouldReturnMessagesForRequiredReceiver() {
        Message firstMessage = new Message("Jason Bourne", "Lara Croft", "message");
        messageDao.addMessage(firstMessage);

        Message secondMessage = new Message("Lara Croft", "Jason Bourne", "message2");
        messageDao.addMessage(secondMessage);

        Message thirdMessage = new Message("Lara Croft", "Jack Ryan", "message3");
        messageDao.addMessage(thirdMessage);

        Message fourthMessage = new Message("Jack Ryan", "Lara Croft", "message4");
        messageDao.addMessage(fourthMessage);

        assertEquals(4, messageDao.getNumberOfMessagesStored());
        assertEquals(2, messageDao.getMessagesForRecipientId("Lara Croft").size());
        assertEquals(1, messageDao.getMessagesForRecipientId("Jason Bourne").size());
        assertEquals(1, messageDao.getMessagesForRecipientId("Jack Ryan").size());
    }

    @Test
    public void shouldRemoveMessagesForReceiver() {
        Message firstMessage = new Message("Jason Bourne", "Lara Croft", "message");
        messageDao.addMessage(firstMessage);

        Message secondMessage = new Message("Lara Croft", "Jason Bourne", "message2");
        messageDao.addMessage(secondMessage);

        Message thirdMessage = new Message("Lara Croft", "Jack Ryan", "message3");
        messageDao.addMessage(thirdMessage);

        Message fourthMessage = new Message("Jack Ryan", "Lara Croft", "message4");
        messageDao.addMessage(fourthMessage);

        boolean removed = messageDao.removeMessagesForUser("Lara Croft");
        assertTrue(removed);

        assertEquals(2, messageDao.getNumberOfMessagesStored());
        assertEquals(0, messageDao.getMessagesForRecipientId("Lara Croft").size());
    }
}
