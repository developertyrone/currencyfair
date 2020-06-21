package com.currencyfair.service.api;

import com.currencyfair.service.ServiceApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


@ContextConfiguration(classes = ServiceApplication.class)
@TestPropertySource(value = {"classpath:application-local.properties"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MessageTest {
    @Value("${server.port}")
    int port;

    @Before
    public void setBaseUri() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost"; // replace as appropriate
    }

    //Test get all response with mocks in data.sql
    @Test
    public void whenGetAllMessages_thenMessagesReturned() {
        setBaseUri();

        when().
                get("/api/messages").
                then().
                assertThat().
                statusCode(200).
                and().
                contentType(ContentType.JSON);
    }

    //Test insert  message
    @Test
    public void whenCreateMessages_thenOK() throws JSONException {
        setBaseUri();
        /*"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP",
                "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471,
                "timePlaced" : "24-JAN-18 10:27:44", "originatingCountry" : "FR"*/
        JSONObject jsonObj = new JSONObject()
                .put("userId", "123256")
                .put("currencyFrom", "HKD")
                .put("currencyTo", "GBP")
                .put("amountSell", 1000)
                .put("amountBuy", 756.10)
                .put("rate", 0.7561)
                .put("timePlaced", "24-DEC-18 08:37:24")
                .put("originatingCountry", "FR");


        given()
                .contentType("application/json")  //another way to specify content type
                .body(jsonObj.toString()).   // use jsonObj toString method.
                when()
                .post("/api/messages").
                then()
                .assertThat()
                .statusCode(201).
                and()
                .contentType(ContentType.JSON);

    }

    //Test insert invalid message
    @Test
    public void whenCreateInvalidMessages_thenServiceUnavailable() throws JSONException {
        setBaseUri();

        JSONObject jsonObj = new JSONObject()
                .put("userId", "123256")
                .put("currencyFrom", "HKD")
                .put("currencyTo", "GBP")
                .put("amountSell", 1000)
                .put("amountBuy", 756.10)
                .put("rate", 0.7561)
                .put("invalidField", "24-DEC-18 08:37:24")
                .put("originatingCountry", "FR");


        given()
                .contentType("application/json")  //another way to specify content type
                .body(jsonObj.toString()).   // use jsonObj toString method.
                when()
                .post("/api/messages").
                then()
                .assertThat()
                .statusCode(503).
                and()
                .contentType(ContentType.JSON);

    }

    //Test Messages after Insert
    @Test
    public void whenGetUserMessagesAfterInsert_thenOKandReturnCorrectCount() {
        setBaseUri();

        when().
                get("/api/messages/userId/123256").
                then().
                assertThat().
                statusCode(200).
                and().
                contentType(ContentType.JSON).
                and().
                body("size()", is(1));
    }

    //Test Message for a invalid user
    @Test
    public void whenGetUserMessagesForInvalidUser_thenReturnNotFound() {
        setBaseUri();

        when().
                get("/api/messages/userId/123").
                then().
                assertThat().
                statusCode(404).
                and().
                contentType(ContentType.JSON).
                and().
                body("error", equalTo("Not Found"));
    }
    
    //Test Count of Message with mocks in data.sql
    @Test
    public void whenGetAllMessagesAfterInsert_thenOKandReturnCorrectCount() {
        setBaseUri();

        when().
                get("/api/messages").
                then().
                assertThat().
                statusCode(200).
                and().
                contentType(ContentType.JSON).
                and().
                body("size()", is(6));
    }

    //[{"country":"FR","count":3},{"country":"HK","count":1},{"country":"US","count":2}]

    //Test Get Statistics by Country
    @Test
    public void whenGetCountryStatistics_thenOKandReturnCorrectStatistics() {
        setBaseUri();

        when().
                get("/api/messages/country").
                then().
                assertThat().
                statusCode(200).
                and().
                contentType(ContentType.JSON).
                and().
                body("find { it.country == 'FR' }.count", equalTo(3));

    }



}
