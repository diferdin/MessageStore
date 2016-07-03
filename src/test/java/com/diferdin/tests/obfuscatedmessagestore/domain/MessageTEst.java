package com.diferdin.tests.obfuscatedmessagestore.domain;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class MessageTest {

    @Test
    public void shouldGetFields() {
        Message message = new Message("sender", "receiver", "message");

        assertEquals("sender", message.getSender());
        assertEquals("receiver", message.getReceiver());
        assertEquals("message", message.getMessage());
    }

    @Test
    public void shouldEqualMessage() {
        Message message = new Message("sender", "receiver", "message");
        Message secondMessage = new Message("sender", "receiver", "message");

        assertTrue(message.equals(secondMessage));
    }

    @Test
    public void shouldNotEqualMessage() {
        Message message = new Message("sender", "receiver", "message");
        Message secondMessage = new Message("sender", "receiver", "message2");

        assertFalse(message.equals(secondMessage));
    }
}
