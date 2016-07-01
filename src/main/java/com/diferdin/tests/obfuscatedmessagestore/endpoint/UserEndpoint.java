package com.diferdin.tests.obfuscatedmessagestore.endpoint;

import com.diferdin.tests.obfuscatedmessagestore.domain.User;
import com.diferdin.tests.obfuscatedmessagestore.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@RestController
public class UserEndpoint {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users",
            method = POST,
            produces = "application/json")
    public ResponseEntity<String> createUser(@RequestBody String jsonName) throws IOException {

        User user = new ObjectMapper().readValue(new StringReader(jsonName), User.class);

        if(userService.addUser(user)) {
            ObjectMapper mapper = getFilteredMapper("uuid");
            return new ResponseEntity<>(mapper.writeValueAsString(user), HttpStatus.CREATED);
        }

        return new ResponseEntity<>("User already in store.", HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/users/{uuid}",
            method = DELETE,
            produces = "application/json")
    public ResponseEntity<String> deleteUser(@PathVariable String uuid) {

        if(userService.removeUser(uuid)) {
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>("Could not delete", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/users/{uuid}",
            method = GET,
            produces = "application/json")
    public ResponseEntity<String> getUser(@PathVariable String uuid) throws JsonProcessingException {
        User retrieved = userService.getUser(uuid);
        if(retrieved != null) {
            ObjectMapper mapper = getFilteredMapper("name");
            return new ResponseEntity<>(mapper.writeValueAsString(retrieved), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/users",
            method = GET,
            produces = "application/json")
    public ResponseEntity<String> getUsers() throws JsonProcessingException {
        List<User> userNames = userService.getAllUsers();

        ObjectMapper mapper = getFilteredMapper("name");

        return new ResponseEntity<>(mapper.writeValueAsString(userNames), HttpStatus.OK);
    }

    private ObjectMapper getFilteredMapper(String selectedField) {
        ObjectMapper mapper = new ObjectMapper();
        FilterProvider filters = new SimpleFilterProvider().addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept(selectedField));
        mapper.setFilters(filters);

        return mapper;
    }
}
