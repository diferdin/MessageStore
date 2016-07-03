package com.diferdin.tests.obfuscatedmessagestore.service.obfuscation;

import com.diferdin.tests.obfuscatedmessagestore.domain.Message;

public class Rot20Obfuscator extends Obfuscator{

    private String rotatedAlphabet;

    public Rot20Obfuscator() {
        super();

        rotatedAlphabet = rotateStandardAlphabet();
    }

    public Message obfuscate(Message message) {
        char[] textChars = message.getMessage().toCharArray();
        char[] obfuscatedText = new char[textChars.length];

        for(int i = 0; i < textChars.length; i++) {
            //check letter index on standard alphabet
            int indexOnStandardAlphabet = standardAlphabet.indexOf(textChars[i]);

            //take index and check rotated alphabet
            obfuscatedText[i] = rotatedAlphabet.charAt(indexOnStandardAlphabet);
        }
        return new Message(message.getSender(), message.getReceiver(), new String(obfuscatedText));
    }

    private String rotateStandardAlphabet() {
        char[] newAlphabet = new char[standardAlphabet.length()];
        char[] standaralphabetChar = standardAlphabet.toCharArray();
        for(int i = 0; i < standardAlphabet.length(); i++) {
            newAlphabet[i] = standaralphabetChar[(i+20) % 26];
        }

        return new String(newAlphabet);
    }
}
