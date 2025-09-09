package com.example.fitnessserver;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Model model; // Для @ModelAttribute

    private HttpSession session;

    @BeforeEach
    public void setUp() {
        session = new MockHttpSession();
    }

    @Test
    public void testShowLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testProcessLoginWithEmptyUserId() throws Exception {
        mockMvc.perform(post("/login")
                        .param("user_id", "")
                        .session((MockHttpSession) session))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    public void testProcessLoginWithValidUserId() throws Exception {
        String userId = "user_123";

        mockMvc.perform(post("/login")
                        .param("user_id", userId)
                        .session((MockHttpSession) session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user"));

        // Проверяем, что в сессии появился объект User
        User user = (User) session.getAttribute("user");
        assertNotNull(user);
        assertEquals(userId, user.getUserId());
    }
}
