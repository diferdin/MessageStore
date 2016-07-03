package com.diferdin.tests.obfuscatedmessagestore.service;

import com.diferdin.tests.obfuscatedmessagestore.domain.Message;
import com.diferdin.tests.obfuscatedmessagestore.service.obfuscation.Rot20Obfuscator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertEquals;

public class Rot20ObfuscationTest {

    @InjectMocks
    private Rot20Obfuscator obfuscator;

    @Before
    public void configureTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldObfuscateClearText() {
        Message clearMessage = new Message("sender-uuid", "receiver-uuid", "ZETA");

        Message obfuscatedMessage = obfuscator.obfuscate(clearMessage);

        assertEquals("sender-uuid", obfuscatedMessage.getSender());
        assertEquals("receiver-uuid", obfuscatedMessage.getReceiver());
        assertEquals("TYNU", obfuscatedMessage.getMessage());
    }

    @Test
    public void shouldClarifyObfuscatedText() {
        Message obfuscatedMessage = new Message("sender-uuid", "receiver-uuid", "TYNU");

        Message clearMessage = obfuscator.clarify(obfuscatedMessage);

        assertEquals("sender-uuid", obfuscatedMessage.getSender());
        assertEquals("receiver-uuid", obfuscatedMessage.getReceiver());
        assertEquals("ZETA", clearMessage.getMessage());
    }
}
