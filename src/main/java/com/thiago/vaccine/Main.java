package com.thiago.vaccine;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        System.setProperty("webdriver.chrome.args", "--disable-logging");

        String url = "https://events.juvare.com/IL-IDPH/4f10bfeb-6d6a-4a9b-9059-1e06b405743f";

        WebDriver driver = new ChromeDriver();

        driver.get(url);

        boolean isPresent = true;
        while (isPresent) {
            isPresent = exists(driver, By.cssSelector("#body > main > h1")).isPresent();
            if (!isPresent) {
                exists(driver, By.xpath("//*[text()[contains(.,'Book now')]]")).ifPresent(WebElement::click);
                exists(driver, By.xpath("//*[text()[contains(.,'Add to cart')]]")).ifPresent(WebElement::click);

                System.out.println("!!! Entered the form !!!");
            }
            isPresent = true;
        }

    }


    private static Optional<WebElement> exists(WebDriver driver, By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            return Optional.of(driver.findElement(by));
        } catch (TimeoutException | NoSuchElementException e) {
            driver.navigate().refresh();
            return Optional.empty();
        }
    }
}
