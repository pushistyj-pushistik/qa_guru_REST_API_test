package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.App;
import helpers.AllureAttachments;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static io.qameta.allure.Allure.step;

public class TestBase {
    @BeforeAll
    static void beforeAll() {

        RestAssured.baseURI = App.config.baseURI();
        Configuration.baseUrl = App.config.baseUrl();

        String selenoidUrl = App.config.selenoidUrl();
            if (selenoidUrl != null) {
                step("Remote web driver setup", () -> {
                Configuration.remote = "https://" + App.config.selenoidlogin() + ":" + App.config.selenoidpassword() + "@selenoid.autotests.cloud/wd/hub";
        });
      }
    }

    @BeforeEach
    public void beforeEach() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void afterEach() {
        AllureAttachments.screenshotAs("Last screenshot");
        AllureAttachments.pageSource();
        AllureAttachments.browserConsoleLogs();
        AllureAttachments.addVideo();
        closeWebDriver();
    }
}
