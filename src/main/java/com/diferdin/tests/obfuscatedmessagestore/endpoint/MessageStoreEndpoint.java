package com.diferdin.tests.obfuscatedmessagestore.endpoint;

import com.diferdin.tests.obfuscatedmessagestore.domain.Message;
import com.diferdin.tests.obfuscatedmessagestore.service.MessageService;
import com.diferdin.tests.obfuscatedmessagestore.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class MessageStoreEndpoint {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/messages",
            method = POST,
            produces = "application/json")
    public ResponseEntity<String> postMessage(@RequestBody String messageDetails) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Message inputMessage = mapper.readValue(messageDetails, Message.class);

        if(userIsUnknown(inputMessage.getSender()) || userIsUnknown(inputMessage.getReceiver())) {
            return new ResponseEntity<>("User unknown.", HttpStatus.NOT_FOUND);
        }

        if(!isValid(inputMessage.getMessage())) {
            return new ResponseEntity<>("Sender or recipient are not existing users.", HttpStatus.BAD_REQUEST);
        }

        if(messageService.addMessage(inputMessage)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean isValid(String message) {
        Character[] chars = message.chars().mapToObj(c -> (char)c).toArray(Character[]::new);

        for(Character myChar : chars) {
            if(Character.isLowerCase(myChar)) {
                return false;
            }
        }

        return true;
    }

    private boolean userIsUnknown(String userId) {
        return userService.containsUser(userId);
    }
}
