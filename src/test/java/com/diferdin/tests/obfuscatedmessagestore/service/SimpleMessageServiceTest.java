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
}
