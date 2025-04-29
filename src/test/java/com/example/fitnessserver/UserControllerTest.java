package com.example.fitnessserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUserPage_WithUserInSession() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", new User("testUser"));

        mockMvc.perform(get("/user").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("user"))
                .andExpect(model().attribute("userId", "testUser"));
    }

    @Test
    public void testUserPage_WithoutUserInSession() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}
