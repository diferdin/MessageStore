package com.diferdin.tests.obfuscatedmessagestore.endpoint.test;

import com.diferdin.tests.obfuscatedmessagestore.endpoint.UserEndpoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class UserEndpointTest {

//    @Autowired
//    WebApplicationContext wac;

    @Autowired
    MockHttpSession session;

    @Autowired
    MockHttpServletRequest request;

    private MockMvc mockMvc;

    @Before
    public void setup() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mockMvc = MockMvcBuilders.standaloneSetup(new UserEndpoint()).build();
    }

    @Test
    public void shouldCreateUser() throws Exception {
        mockMvc.perform(post("/users").session(session)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
