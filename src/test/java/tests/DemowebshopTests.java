package tests;

import com.codeborne.selenide.Configuration;
import config.App;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static helpers.CustomApiListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Cookie;


public class DemowebshopTests {

    static String login,
            password,
            authCookieName = "NOPCOMMERCE.AUTH";

    @BeforeAll
    static void configure() {

        RestAssured.baseURI = App.config.baseURI();
        Configuration.baseUrl = App.config.baseUrl();

        login = App.config.login();
        password = App.config.password();
    }

    @Test
    @DisplayName("Successful authorization to some demowebshop (UI)")
    void loginTest() {
        step("Open login page", () ->
                open("/login"));

        step("Fill login form", () -> {
            $("#Email").setValue(login);
            $("#Password").setValue(password)
                    .pressEnter();
        });

        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(login)));
    }

    @Test
    @DisplayName("Successful authorization to some demowebshop (API + UI)")
    void loginWithApiTest() {
        step("Get cookie by api and set it to browser", () -> {
            String authCookieValue = given()
                    .filter(withCustomTemplates())
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("Email", login)
                    .formParam("Password", password)
                    .log().all()
                    .when()
                    .post("/login")
                    .then()
                    .log().all()
                    .statusCode(302)
                    .extract().cookie(authCookieName);

            step("Open minimal content, because cookie can be set when site is opened", () ->
                    open("/Themes/DefaultClean/Content/images/logo.png"));
            step("Set cookie to to browser", () -> {
                Cookie authCookie = new Cookie(authCookieName, authCookieValue);
                WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
            });
        });

        step("Open main page", () ->
                open(""));
        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(login)));
    }

    @Test
    @DisplayName("Adding a product to the cart (API + UI1)")
    void addProductToCartTest() {
        String authCookieValue = given()
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded")
                .formParam("Email", login)
                .formParam("Password", password)
                .log().all()
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(302)
                .extract().cookie(authCookieName);

         given()
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded")
                .log().all()
                .when()
                .post("/addproducttocart/catalog/31/1/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"));


        step("Open minimal content, because cookie can be set when site is opened", () ->
                open("/Themes/DefaultClean/Content/images/logo.png"));
        step("Set cookie to to browser", () -> {
            Cookie authCookie = new Cookie(authCookieName, authCookieValue);
            WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
        });

        step("Open cart page", () ->
                open("/cart"));
        step("Check if the item is in the cart", () ->
                $(".page-body").shouldHave(text("14.1-inch Laptop")));
    }
}


