package com.testingAcademy.base;

import Endpoints.APIConstants;
import com.testingAcademy.Aserts.AssertActions;
import com.testingAcademy.Modules.PayloadManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;

public class base {
    public RequestSpecification requestSpecification;
    public AssertActions assertActions;
    public PayloadManager payloadManager;
    public JsonPath jsonPath;
    public Response response;
    public ValidatableResponse validatableResponse;

    //  TC - Header

    @BeforeTest
    public void setUp() {
        payloadManager = new PayloadManager();
        assertActions = new AssertActions();
        requestSpecification = new RestAssured().given()
                .baseUri(APIConstants.BASE_URL).contentType(ContentType.JSON).log().all();


    }
    public String getToken() {
        requestSpecification = RestAssured
                .given()
                .baseUri(APIConstants.BASE_URL)
                .basePath(APIConstants.AUTH_URL);

        // Setting the payload
        String payload = payloadManager.setAuthPayload();

        // Get the Token
        response = requestSpecification.contentType(ContentType.JSON).body(payload).when().post();

        // String Extraction
        String token = payloadManager.getTokenFromJSON(response.asString());

        return token;
    }

}
