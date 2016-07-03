package com.diferdin.tests.obfuscatedmessagestore.endpoint;

import com.diferdin.tests.obfuscatedmessagestore.domain.Message;
import com.diferdin.tests.obfuscatedmessagestore.domain.User;
import com.diferdin.tests.obfuscatedmessagestore.service.MessageService;
import com.diferdin.tests.obfuscatedmessagestore.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
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

        if(!userIsValid(inputMessage.getSender()) || !userIsValid(inputMessage.getReceiver())) {
            return new ResponseEntity<>("Sender or receiver cannot be null.", HttpStatus.BAD_REQUEST);
        }

        if(!userIsKnown(inputMessage.getSender()) || !userIsKnown(inputMessage.getReceiver())) {
            return new ResponseEntity<>("Sender or receiver could not be validated.", HttpStatus.NOT_FOUND);
        }

        if(!isValid(inputMessage.getMessage())) {
            return new ResponseEntity<>("Message is invalid.", HttpStatus.BAD_REQUEST);
        }

        if(messageService.addMessage(inputMessage)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/messages/users/{receiverUuid}",
            method = GET,
            produces = "application/json")
    public ResponseEntity<String> getMessages(@PathVariable String receiverUuid) throws JsonProcessingException {

        List<Message> messages = messageService.getAllMessagesForReceiverId(receiverUuid);

        ObjectMapper mapper = new ObjectMapper();

        if(messages.isEmpty()) {
            return new ResponseEntity<>(mapper.writeValueAsString(messages), HttpStatus.NO_CONTENT);
        }

        List<Message> messagesWithNames = replaceIdsWithNames(messages);

        return new ResponseEntity<>(mapper.writeValueAsString(messagesWithNames), HttpStatus.OK);
    }

    private boolean isValid(String message) {

        if(message == null) {
            return false;
        }

        Character[] chars = message.chars().mapToObj(c -> (char)c).toArray(Character[]::new);

        for(Character myChar : chars) {
            if(Character.isLowerCase(myChar)) {
                return false;
            }
        }

        return true;
    }

    private boolean userIsValid(String userId) {
        return userId != null;
    }

    private boolean userIsKnown(String userId) {
        return userService.containsUser(userId);
    }

    private List<Message> replaceIdsWithNames(List<Message> messages) {
        return messages.stream().map(this::replaceIdsWithNamesOnMessage).collect(Collectors.toList());
    }

    private Message replaceIdsWithNamesOnMessage(Message message) {
        User sender = userService.getUser(message.getSender());
        User receiver = userService.getUser(message.getReceiver());

        return new Message(sender.getName(), receiver.getName(), message.getMessage());
    }
}
