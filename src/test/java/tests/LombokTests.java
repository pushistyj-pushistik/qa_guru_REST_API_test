package tests;

import model.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tests.Specs.requestSpec;
import static tests.Specs.responseSpec;
import static io.restassured.RestAssured.given;

public class LombokTests {
    @Test
    void singleUserTest() {
        given()
                .spec(requestSpec)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSpec)
                .log().body();
    }

    @Test
    void singleUserWithLombokModel() {
        UserData data = given()
                .spec(requestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(responseSpec)
                .log().body()
                .extract().as(UserData.class);
        assertEquals(8, data.getUser().getId());
    }

}
