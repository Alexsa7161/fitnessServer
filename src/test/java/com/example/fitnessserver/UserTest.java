package com.example.fitnessserver;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    private final UserController controller = new UserController();

    @Test
    public void testUserIsNull() {
        Model model = new ConcurrentModel();
        String result = controller.userPage(null, model);

        assertEquals("redirect:/login", result);
        assertEquals("Неизвестный пользователь", model.getAttribute("userId"));
    }

    @Test
    public void testUserIsNotNull() {
        Model model = new ConcurrentModel();
        User user = new User("user_123");

        String result = controller.userPage(user, model);

        assertEquals("user", result);
        assertEquals("user_123", model.getAttribute("userId"));
    }
}
