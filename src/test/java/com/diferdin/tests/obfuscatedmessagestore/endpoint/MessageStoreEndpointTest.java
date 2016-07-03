package com.diferdin.tests.obfuscatedmessagestore.endpoint;

import com.diferdin.tests.obfuscatedmessagestore.config.MvcConfiguration;
import com.diferdin.tests.obfuscatedmessagestore.domain.Message;
import com.diferdin.tests.obfuscatedmessagestore.domain.User;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        doReturn(true).when(userService).containsUser("sender-uuid");
        doReturn(true).when(userService).containsUser("receiver-uuid");
        doReturn(true).when(messageService).addMessage(new Message("sender-uuid", "receiver-uuid", "WHATEVERITIS"));

        mockMvc.perform(post("/messages")
                .content("{ \"sender\": \"sender-uuid\", \"receiver\": \"receiver-uuid\", \"message\": \"WHATEVERITIS\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnNotFoundIfReceiverIsUnknown() throws Exception {

        doReturn(true).when(userService).containsUser("sender-uuid");

        mockMvc.perform(post("/messages")
                .content("{ \"sender\": \"sender-uuid\", \"receiver\": \"receiver-uuid\", \"message\": \"WHATEVERITIS\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnNotFoundIfSenderIsUnknown() throws Exception {

        doReturn(true).when(userService).containsUser("receiver-uuid");

        mockMvc.perform(post("/messages")
                .content("{ \"sender\": \"sender-uuid\", \"receiver\": \"receiver-uuid\", \"message\": \"WHATEVERITIs\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnBadRequestIfSenderIsNotSpecified() throws Exception {

        mockMvc.perform(post("/messages")
                .content("{ \"receiver\": \"receiver-uuid\", \"message\": \"WHATEVERITIS\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void shouldReturnBadRequestIfReceiverIsNotSpecified() throws Exception {
        mockMvc.perform(post("/messages")
                .content("{ \"sender\": \"sender-uuid\", \"message\": \"WHATEVERITIS\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestIfMessageIsNotSpecified() throws Exception {

        doReturn(true).when(userService).containsUser("sender-uuid");
        doReturn(true).when(userService).containsUser("receiver-uuid");

        mockMvc.perform(post("/messages")
                .content("{ \"sender\": \"sender-uuid\", \"receiver\": \"receiver-uuid\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnInternalServerErrorIfCannotAddMessage() throws Exception {

        doReturn(true).when(userService).containsUser("sender-uuid");
        doReturn(true).when(userService).containsUser("receiver-uuid");
        doReturn(false).when(messageService).addMessage(new Message("sender-uuid", "receiver-uuid", "WHATEVERITIS"));

        mockMvc.perform(post("/messages")
                .content("{ \"sender\": \"sender-uuid\", \"receiver\": \"receiver-uuid\", \"message\": \"WHATEVERITIS\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void shouldReturnOkIfMessagesAreStored() throws Exception {

        List<Message> messages = new ArrayList<>();

        Message message1 = new Message("user1Id", "user2Id", "HEY");
        messages.add(message1);

        Message message2 = new Message("user1Id", "user2Id", "HOWAREYOU");
        messages.add(message2);

        doReturn(messages).when(messageService).getAllMessagesForReceiverId("user2Id");
        doReturn(new User("Jason Bourne")).when(userService).getUser("user1Id");
        doReturn(new User("Lara Croft")).when(userService).getUser("user2Id");

        MvcResult getResult = mockMvc.perform(get("/messages/users/user2Id")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonMessageList = getResult.getResponse().getContentAsString();

        assertEquals("[{\"sender\":\"Jason Bourne\",\"receiver\":\"Lara Croft\",\"message\":\"HEY\"},{\"sender\":\"Jason Bourne\",\"receiver\":\"Lara Croft\",\"message\":\"HOWAREYOU\"}]", jsonMessageList);
    }

    @Test
    public void shouldReturnNoContentIfMessagesNotStored() throws Exception {
        doReturn(Collections.emptyList()).when(messageService).getAllMessagesForReceiverId("user2Id");
        doReturn(new User("Jason Bourne")).when(userService).getUser("user1Id");
        doReturn(new User("Lara Croft")).when(userService).getUser("user2Id");

        MvcResult getResult = mockMvc.perform(get("/messages/users/user2Id")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        String jsonMessageList = getResult.getResponse().getContentAsString();

        assertEquals("[]", jsonMessageList);
    }
}
