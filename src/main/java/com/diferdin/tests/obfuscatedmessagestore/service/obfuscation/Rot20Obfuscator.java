package com.diferdin.tests.obfuscatedmessagestore.service.obfuscation;

import com.diferdin.tests.obfuscatedmessagestore.domain.Message;

public class Rot20Obfuscator extends Obfuscator{

    private String rotatedAlphabet;

    public Rot20Obfuscator() {
        super();

        rotatedAlphabet = rotateStandardAlphabet();
    }

    public Message obfuscate(Message message) {

        String obfuscatedText = applyChange(message.getMessage(), standardAlphabet, rotatedAlphabet);
        return new Message(message.getSender(), message.getReceiver(), new String(obfuscatedText));
    }

    public Message clarify(Message message) {

        String clarifiedText = applyChange(message.getMessage(), rotatedAlphabet, standardAlphabet);
        return new Message(message.getSender(), message.getReceiver(), new String(clarifiedText));
    }

    private String applyChange(String text, String startingAlphabet, String destinationAlphabet) {
        char[] inputChars = text.toCharArray();
        char[] outputChars = new char[inputChars.length];

        for(int i = 0; i < inputChars.length; i++) {
            int indexOnStartingAlphabet = startingAlphabet.indexOf(inputChars[i]);

            outputChars[i] = destinationAlphabet.charAt(indexOnStartingAlphabet);
        }

        return new String(outputChars);
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
