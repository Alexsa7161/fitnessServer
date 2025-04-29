package com.example.fitnessserver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FitnessTrackerTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Настройка ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Убери этот аргумент, если хочешь видеть окно
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        // Инициализация ChromeDriver (местный драйвер)
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("http://localhost:8080");
    }

    @Test
    public void testWebSocketConnection() {
        driver.findElement(By.id("connectBtn")).click();
        String metricsText = driver.findElement(By.id("metrics")).getText();
        assertTrue(metricsText.contains("\uD83D\uDCCA heart_rate"));
    }

    @Test
    public void testToggleHistory() {
        driver.findElement(By.id("toggleHistoryBtn")).click();
        String historyBlockText = driver.findElement(By.id("historyBlock")).getText();
        assertTrue(historyBlockText.contains("История пользователя"));
    }

    @Test
    public void testLoadHistoryByDate() {
        driver.findElement(By.id("toggleHistoryBtn")).click();
        driver.findElement(By.id("startTime")).sendKeys("2025-04-01T00:00");
        driver.findElement(By.id("endTime")).sendKeys("2025-04-29T00:00");
        driver.findElement(By.id("loadHistoryBtn")).click();
        String historyListText = driver.findElement(By.id("historyList")).getText();
        assertTrue(historyListText.contains("Нет данных") || historyListText.contains("heart_rate"));
    }

    @Test
    public void testAddRecord() {
        driver.findElement(By.id("newUserId")).sendKeys("user_1");
        driver.findElement(By.id("newMetric")).sendKeys("heart_rate");
        driver.findElement(By.id("newValue")).sendKeys("75");
        driver.findElement(By.id("addRecordBtn")).click();
        String message = driver.findElement(By.id("messages")).getText();
        assertTrue(message.contains("✅ Запись добавлена успешно!"));
    }

    @Test
    public void testDeleteRecord() {
        driver.findElement(By.id("toggleHistoryBtn")).click();
        driver.findElement(By.cssSelector(".history-item button")).click();
        String message = driver.findElement(By.id("messages")).getText();
        assertTrue(message.contains("✅ Запись удалена!"));
    }

    @Test
    public void testEditRecord() {
        driver.findElement(By.id("toggleHistoryBtn")).click();
        driver.findElement(By.cssSelector(".history-item button:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".edit-field input")).sendKeys("80");
        driver.findElement(By.cssSelector(".edit-field button")).click();
        String message = driver.findElement(By.id("messages")).getText();
        assertTrue(message.contains("✅ Запись обновлена!"));
    }

    @Test
    public void testInvalidUserId() {
        driver.findElement(By.id("connectBtn")).click();
        String message = driver.findElement(By.id("messages")).getText();
        assertTrue(message.contains("❗ Введите User ID!"));
    }

    @Test
    public void testWebSocketError() {
        driver.findElement(By.id("connectBtn")).click();
        String message = driver.findElement(By.id("messages")).getText();
        assertTrue(message.contains("Ошибка подключения к WebSocket."));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
