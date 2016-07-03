package com.diferdin.tests.obfuscatedmessagestore.domain;

public class Message {

    private String sender;
    private String receiver;
    private String message;

    public Message(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public Message() {
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object object) {
        if(object == null) {
            return false;
        }

        if(!Message.class.isAssignableFrom(object.getClass())) {
            return false;
        }

        final Message message = (Message) object;

        return sender.equals(message.getSender()) &&
                receiver.equals(message.getReceiver()) &&
                this.message.equals(message.getMessage());
    }

    @Override
    public int hashCode() {
        return message.hashCode();
    }
}
