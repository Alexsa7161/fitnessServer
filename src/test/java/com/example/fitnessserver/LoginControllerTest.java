package com.example.fitnessserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testShowLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testProcessLoginWithEmptyUserId() throws Exception {
        mockMvc.perform(post("/login")
                        .param("user_id", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "Введите ID пользователя."));
    }

    @Test
    public void testProcessLoginWithValidUserId() throws Exception {
        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(post("/login")
                        .param("user_id", "user_123")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user"));

        // Проверка, что сессия содержит объект User
        Object userAttr = session.getAttribute("user");
        assertNotNull(userAttr, "User object should be in session");
        assertTrue(userAttr instanceof User, "Session attribute should be of type User");
        assertEquals("user_123", ((User) userAttr).getUserId(), "User ID should match the input");
    }
}
