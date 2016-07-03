package com.diferdin.tests.obfuscatedmessagestore.endpoint;

import com.diferdin.tests.obfuscatedmessagestore.config.MvcConfiguration;
import com.diferdin.tests.obfuscatedmessagestore.domain.User;
import com.diferdin.tests.obfuscatedmessagestore.service.SimpleUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.doReturn;

import static org.mockito.Matchers.any;

import static junit.framework.TestCase.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = MvcConfiguration.class)
public class UserEndpointTest {

    private MockMvc mockMvc;

    @Mock
    SimpleUserService userService;

    @InjectMocks
    UserEndpoint userEndpoint;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userEndpoint).build();
    }

    @Test
    public void shouldReturnCreatedOnCreateUser() throws Exception {

        doReturn(true).when(userService).addUser(any(User.class));

        mockMvc.perform(post("/users")
                .content("{ \"name\": \"Jason Bourne\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnNoContentOnDeletingExistingUser() throws Exception {

        doReturn(true).when(userService).addUser(any(User.class));

        MvcResult postResult = mockMvc.perform(post("/users")
                .content("{ \"name\": \"Jason Bourne\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonUuidAdded = postResult.getResponse().getContentAsString();

        String realUuid = new ObjectMapper().readValue(new StringReader(jsonUuidAdded), User.class).getUuid();

        doReturn(true).when(userService).removeUser(realUuid);

        mockMvc.perform(delete("/users/"+realUuid)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnNotFoundOnDeletingNonExistingUser() throws Exception {
        mockMvc.perform(delete("/users/mock_uuid")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnOkOnGettingExistingUser() throws Exception {
        doReturn(true).when(userService).addUser(any(User.class));

        MvcResult postResult = mockMvc.perform(post("/users")
                .content("{ \"name\": \"Jack Ryan\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonUuidAdded = postResult.getResponse().getContentAsString();

        String realUuid = new ObjectMapper().readValue(new StringReader(jsonUuidAdded), User.class).getUuid();

        doReturn(new User("Jack Ryan")).when(userService).getUser(realUuid);

        mockMvc.perform(get("/users/"+realUuid)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundOnGettingNonExistingUser() throws Exception {
        mockMvc.perform(get("/users/mock_uuid")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldReturnOkOnGettingAllUsers() throws Exception {

        List<User> users = new ArrayList<>();

        User jason = new User("Jason Bourne");
        User lara = new User("Lara Croft");

        users.add(jason);
        users.add(lara);

        doReturn(users).when(userService).getAllUsers();

        MvcResult getResult = mockMvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String usersString = getResult.getResponse().getContentAsString();

        assertEquals("[{\"name\":\"Jason Bourne\"},{\"name\":\"Lara Croft\"}]", usersString);
    }

    @Test
    public void shouldReturnOkWhenNoUsersReRegistered() throws Exception {
        MvcResult getResult = mockMvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String usersString = getResult.getResponse().getContentAsString();

        assertEquals("[]", usersString);
    }

}
