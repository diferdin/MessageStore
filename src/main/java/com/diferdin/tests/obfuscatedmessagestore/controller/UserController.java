package com.diferdin.tests.obfuscatedmessagestore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class UserController {

    @RequestMapping(value = "/users",
            method = POST,
            produces = "application/json")
    public String createUser(@RequestParam(value = "name", required = false) String name, Model model) {
        String result = "null";
        model.addAttribute("name", name);
        try {
            result = new ObjectMapper().writeValueAsString(model);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(value = "/users",
            method = DELETE,
            produces = "application/json")
    public void deleteUser(@RequestParam(value = "name") String name, Model model) {

    }

    @RequestMapping(value = "/users",
            method = GET,
            produces = "application/json")
    public String getUser(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}
