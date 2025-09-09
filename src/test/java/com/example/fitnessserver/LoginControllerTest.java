package com.example.fitnessserver;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HttpSession session;

    // Заглушка шаблона
    @Test
    public void testShowLoginPage_stub() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                });
    }

    @Test
    public void testProcessLoginWithEmptyUserId_stub() throws Exception {
        mockMvc.perform(post("/login").param("user_id", ""))
                .andExpect(status().isOk())
                .andExpect(result -> {
                });
    }

    @Test
    public void testProcessLoginWithValidUserId_stub() throws Exception {
        mockMvc.perform(post("/login").param("user_id", "user_1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(result -> {
                });
    }
}
