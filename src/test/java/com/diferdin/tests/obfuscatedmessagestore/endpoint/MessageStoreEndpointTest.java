package com.diferdin.tests.obfuscatedmessagestore.endpoint;

import com.diferdin.tests.obfuscatedmessagestore.config.MvcConfiguration;
import com.diferdin.tests.obfuscatedmessagestore.domain.Message;
import com.diferdin.tests.obfuscatedmessagestore.service.MessageService;
import com.diferdin.tests.obfuscatedmessagestore.service.UserService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = MvcConfiguration.class)
public class MessageStoreEndpointTest {

    private MockMvc mockMvc;

    @Mock
    MessageService messageService;

    @Mock
    UserService userService;

    @InjectMocks
    MessageStoreEndpoint messageStoreEndpoint;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(messageStoreEndpoint).build();
    }

    @Test
    public void shouldReturnCreatedOnAddingMessage() throws Exception {

        doReturn(true).when(userService).containsUser("senderId");
        doReturn(true).when(userService).containsUser("receiverId");
        doReturn(true).when(messageService).addMessage(new Message("sender-uuid", "receiver-uuid", "WHATEVERITIS"));

        mockMvc.perform(post("/messages")
                .content("{ \"sender\": \"sender-uuid\", \"receiver\": \"receiver-uuid\", \"message\": \"WHATEVERITIS\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnNotFoundOnUnknownUser() throws Exception {

        doReturn(false).when(userService).containsUser("receiver-uuid");
        doReturn(true).when(userService).containsUser("sender-uuid");

        mockMvc.perform(post("/messages")
                .content("{ \"sender\": \"sender-uuid\", \"receiver\": \"receiver-uuid\", \"message\": \"WHATEVERITIS\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnBadRequestOnInvalidMessage() throws Exception {

        doReturn(false).when(userService).containsUser("receiver-uuid");
        doReturn(false).when(userService).containsUser("sender-uuid");

        mockMvc.perform(post("/messages")
                .content("{ \"sender\": \"sender-uuid\", \"receiver\": \"receiver-uuid\", \"message\": \"WHATEVERITIs\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnInternalServerErrorOnAddingMessage() throws Exception {

        doReturn(true).when(userService).containsUser("senderId");
        doReturn(true).when(userService).containsUser("receiverId");
        doReturn(false).when(messageService).addMessage(new Message("sender-uuid", "receiver-uuid", "WHATEVERITIS"));

        mockMvc.perform(post("/messages")
                .content("{ \"sender\": \"sender-uuid\", \"receiver\": \"receiver-uuid\", \"message\": \"WHATEVERITIS\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

}
