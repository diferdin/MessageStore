package com.diferdin.tests.obfuscatedmessagestore.service;

import com.diferdin.tests.obfuscatedmessagestore.dao.MessageDao;
import com.diferdin.tests.obfuscatedmessagestore.domain.Message;
import com.diferdin.tests.obfuscatedmessagestore.service.obfuscation.Obfuscator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.mockito.Mockito.doReturn;

import static junit.framework.TestCase.assertTrue;

public class SimpleMessageServiceTest {

    @InjectMocks
    private SimpleMessageService messageService;

    @Mock
    private Obfuscator obfuscator;

    @Spy
    private MessageDao messageDao;

    @Before
    public void configureTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldAddMessage() {
        Message message = new Message("sender-uuid", "receiver-uuid", "message");
        Message obfuscatedMessage = new Message("sender-uuid", "receiver-uuid", "zrffntr");

        doReturn(true).when(messageDao).addMessage(message);
        doReturn(obfuscatedMessage).when(obfuscator).obfuscate(message);

        boolean added = messageService.addMessage(message);

        assertTrue(added);
    }

    @Test
    public void shouldGetAllMessagesForReceiver() {
        Message message = new Message("sender-uuid", "receiver-uuid", "message");
        Message obfuscatedMessage = new Message("sender-uuid", "receiver-uuid", "zrffntr");

        doReturn(obfuscatedMessage).when(obfuscator).obfuscate(message);

        boolean added = messageService.addMessage(message);
        assertTrue(added);

        Message message2 = new Message("sender-uuid", "receiver-uuid", "messagemessage");
        Message obfuscatedMessage2 = new Message("sender-uuid", "receiver-uuid", "zrffntrzrffntr");

        doReturn(obfuscatedMessage2).when(obfuscator).obfuscate(message2);

        boolean added2 = messageService.addMessage(message2);
        assertTrue(added2);

        doReturn(message).when(obfuscator).clarify(obfuscatedMessage);
        doReturn(message2).when(obfuscator).clarify(obfuscatedMessage2);

        List<Message> messages = messageService.getAllMessagesForReceiverId("receiver-uuid");

        assertEquals(2, messages.size());
        assertTrue(messages.contains(message));
        assertTrue(messages.contains(message2));
    }
}
