package com.example.fitnessserver;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    private LoginController loginController;
    private HttpSession session;
    private Model model;

    @BeforeEach
    void setUp() {
        loginController = new LoginController();
        session = mock(HttpSession.class);
        model = new ConcurrentModel();
    }

    @Test
    void testShowLoginPage() {
        String view = loginController.showLoginPage();
        assertEquals("login", view, "Метод должен возвращать имя шаблона login");
    }

    @Test
    void testProcessLoginWithEmptyUserId() {
        String view = loginController.processLogin("", session, model);
        assertEquals("login", view, "При пустом userId должен вернуться login");
        assertTrue(model.containsAttribute("error"), "В модели должно быть сообщение об ошибке");
    }

    @Test
    void testProcessLoginWithValidUserId() {
        String view = loginController.processLogin("user123", session, model);
        assertEquals("redirect:/user", view, "При валидном userId должен быть редирект");
        verify(session, times(1)).setAttribute(eq("user"), any(User.class));
    }
}
