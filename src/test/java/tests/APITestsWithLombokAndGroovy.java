package tests;

import model.CreateUser;
import model.UserData;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.given;
import static tests.Specs.*;

public class APITestsWithLombokAndGroovy {

    @Test
    void lombokListUsersTest() {
        UserData data = given()
                .spec(requestSpec)
                .when()
                .get("/users/9")
                .then()
                .spec(responseSpec)
                .log().body()
                .extract().as(UserData.class);
        assertEquals(9, data.getUser().getId());
        assertEquals("tobias.funke@reqres.in", data.getUser().getEmail());
        assertEquals("Tobias", data.getUser().getFirstName());
    }

    @Test
    void lombokСreateUserTest() {
        CreateUser body = new CreateUser();
        body.setName("morpheus");
        body.setJob("leader");
        given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post("/users")
                .then()
                .spec(postResponseSpec)
                .log().body()
                .extract().as(CreateUser.class);
        assertEquals("morpheus", body.getName());
        assertEquals("leader", body.getJob());
    }

    @Test
    void groovyListUsersTest() {
        given()
                .spec(requestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .log().body()
                .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                        hasItem("tobias.funke@reqres.in"));
    }
}
