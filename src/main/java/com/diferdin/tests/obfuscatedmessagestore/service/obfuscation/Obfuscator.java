package com.diferdin.tests.obfuscatedmessagestore.service.obfuscation;


import com.diferdin.tests.obfuscatedmessagestore.domain.Message;

public abstract class Obfuscator {

    public String standardAlphabet;

    public Obfuscator(){
        standardAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    }

    public abstract Message obfuscate(Message message);

    private int checkCharIndex(char c) {
        return standardAlphabet.indexOf(c);
    }
}
