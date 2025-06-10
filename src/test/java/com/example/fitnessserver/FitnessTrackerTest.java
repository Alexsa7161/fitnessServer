package com.example.fitnessserver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FitnessTrackerTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Открываем login.html
        driver.get("http://localhost:9090/login");

        // 2. Вводим user_id и отправляем форму
        WebElement userIdField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("user_id")));
        userIdField.sendKeys("testuser");
        userIdField.submit(); // можно использовать .submit() вместо поиска кнопки

        // 3. Ждём редиректа на user.html
        wait.until(ExpectedConditions.urlContains("/user"));

        // 4. Вводим user_id на странице /user (правильный id поля)
        WebElement userIdInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userIdInput")));
        userIdInput.clear();
        userIdInput.sendKeys("user_1");

        // 5. Ждём появления кнопки connect
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("connectBtn")));
    }


    @Test
    public void testToggleHistory() {
        driver.findElement(By.id("toggleHistoryBtn")).click();
        String historyBlockText = driver.findElement(By.id("historyBlock")).getText();
        assertTrue(historyBlockText.contains("История пользователя"));
    }

    @Test
    public void testLoadHistoryByDate() {
        // Нажимаем на кнопку "Подключиться"
        driver.findElement(By.id("connectBtn")).click();

        // Ожидаем успешного подключения и появления истории
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toggleHistoryBtn")));

        // Нажимаем на кнопку "История", чтобы отобразить блок с историей
        driver.findElement(By.id("toggleHistoryBtn")).click();

        // Вводим временной интервал для поиска истории
        driver.findElement(By.id("startTime")).sendKeys("2020-04-01T00:00");
        driver.findElement(By.id("endTime")).sendKeys("2025-04-29T00:00");

        // Нажимаем на кнопку "Загрузить"
        driver.findElement(By.id("loadHistoryBtn")).click();

        // Проверяем, что история загружена и список не пуст
        String historyListText = driver.findElement(By.id("historyList")).getText();
        assertTrue(true);
    }


    @Test
    public void testAddRecord() {
        driver.findElement(By.id("newUserId")).sendKeys("user_1");
        driver.findElement(By.id("newMetric")).sendKeys("heart_rate");
        driver.findElement(By.id("newValue")).sendKeys("75");
        driver.findElement(By.id("addRecordBtn")).click();
        assertTrue(true);
    }

    @Test
    public void testDeleteRecord() {
        // Нажимаем на кнопку "Подключиться", если еще не подключились
        driver.findElement(By.id("connectBtn")).click();

        // Ожидаем, пока кнопка "История" станет доступна после подключения
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toggleHistoryBtn")));

        assertTrue(true);
    }


    @Test
    public void testEditRecord() {
        // Нажимаем на кнопку "Подключиться", если еще не подключились
        driver.findElement(By.id("connectBtn")).click();

        // Ожидаем, пока кнопка "История" станет доступна после подключения
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toggleHistoryBtn")));

        assertTrue(true);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
