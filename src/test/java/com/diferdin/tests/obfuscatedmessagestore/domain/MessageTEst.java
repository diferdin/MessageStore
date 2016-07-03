package com.diferdin.tests.obfuscatedmessagestore.domain;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class MessageTest {

    @Test
    public void shouldGetFields() {
        Message message = new Message("senderId", "receiverId", "message");

        assertEquals("senderId", message.getSender());
        assertEquals("receiverId", message.getReceiver());
        assertEquals("message", message.getMessage());
    }

    @Test
    public void shouldEqualMessage() {
        Message message = new Message("senderId", "receiverId", "message");
        Message secondMessage = new Message("senderId", "receiverId", "message");

        assertTrue(message.equals(secondMessage));
    }

    @Test
    public void shouldNotEqualMessage() {
        Message message = new Message("senderId", "receiverId", "message");
        Message secondMessage = new Message("senderId", "receiverId", "message2");

        assertFalse(message.equals(secondMessage));
    }
}
