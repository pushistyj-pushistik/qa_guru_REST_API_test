package tests;

import io.restassured.RestAssured;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class ReqresinTests {
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    void createUserTest() {
        String body = "{ \"name\": \"morpheus\", " +
                "\"job\": \"leader\" }";

        given()
                .log().uri()
                .log().body()
                .body(body)
                .contentType(JSON)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    void singleUserTest() {
        given()
                .log().uri()
                .when()
                .get("users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.first_name", is("Janet"));
    }

    @Test
    void updateUserTest() {
        String body = "{ \"name\": \"morpheus\"," +
                " \"job\": \"zion resident\" }";

        given()
                .log().uri()
                .log().body()
                .body(body)
                .contentType(JSON)
                .when()
                .patch("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    void deleteUserTest() {
        given()
                .log().uri()
                .when()
                .delete("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }

    @Test
    void delayedResponseTest() {
        given()
                .log().uri()
                .when()
                .get("/users?delay=3")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(12))
                .body("data.first_name", hasItem("Emma"));
    }
}
