package com.testingAcademy.tests.integration;

import Endpoints.APIConstants;
import com.testingAcademy.Modules.PayloadManager;
import com.testingAcademy.Modules.Pojos.booking;
import com.testingAcademy.Modules.Pojos.bookingResponse;
import com.testingAcademy.Utils.PropertyReader;
import com.testingAcademy.base.base;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TCIntegrationFlow extends base {
    // Create A Booking, Create a Token
    // Get booking
    // Update the Booking
    // Delete the Booking

    @Test(groups = "integration", priority = 1)
    @Owner("Promode")
    @Description("TC#INT1 - Step 1. Verify that the Booking can be Created")
    public void testCreateBooking(ITestContext iTestContext) {

        iTestContext.setAttribute("token", getToken());

        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        Response response = RestAssured
                .given(requestSpecification)
                .when().body(PayloadManager.createPayloadBookingAsString()).post();

         ValidatableResponse validatableResponse = response.then().log().all();

        // Validatable Assertion
        validatableResponse.statusCode(200);
//        validatableResponse.body("booking.firstname", Matchers.equalTo("Pramod"));

        // DeSer
        bookingResponse bookingResponse = PayloadManager.bookingResponseJava(response.asString());
        // AssertJ
        assertThat(bookingResponse.getBookingid()).isNotNull();
        assertThat(bookingResponse.getBooking().getFirstname()).isNotNull().isNotBlank();
        assertThat(bookingResponse.getBooking().getFirstname()).isEqualTo(PropertyReader.readKey("booking.post.firstname"));


        //  Set the booking ID
        iTestContext.setAttribute("bookingid", bookingResponse.getBookingid());


    }

    @Test(groups = "integration", priority = 2)
    @Owner("Promode")
    @Description("TC#INT1 - Step 2. Verify that the Booking By ID")
    public void testVerifyBookingId(ITestContext iTestContext) {

        // bookingId -
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");


        // GET Req
        String basePathGET = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathGET);

        requestSpecification.basePath(basePathGET);
        Response response = RestAssured
                .given(requestSpecification)
                .when().get();
        ValidatableResponse validatableResponse = response.then().log().all();
        // Validatable Assertion
        validatableResponse.statusCode(200);

        booking booking = PayloadManager.getResponseFromJSON(response.asString());

        assertThat(booking.getFirstname()).isNotNull().isNotBlank();
        assertThat(booking.getFirstname()).isEqualTo(PropertyReader.readKey("booking.get.firstname"));


    }

    @Test(groups = "integration", priority = 3)
    @Owner("Promode")
    @Description("TC#INT1 - Step 3. Verify Updated Booking by ID")
    public void testUpdateBookingByID(ITestContext iTestContext) {
        System.out.println("Token - " + iTestContext.getAttribute("token"));
        String token = (String) iTestContext.getAttribute("token");
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        String basePathPUTPATCH = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathPUTPATCH);


        requestSpecification.basePath(basePathPUTPATCH);
        Response response = RestAssured
                .given(requestSpecification).cookie("token", token)
                .when().body(PayloadManager.fullUpdatePayloadAsString()).put();
        ValidatableResponse validatableResponse = response.then().log().all();
        // Validatable Assertion
        validatableResponse.statusCode(200);

        booking booking1 = PayloadManager.getResponseFromJSON(response.asString());

        assertThat(booking1.getFirstname()).isNotNull().isNotBlank();
        assertThat(booking1.getFirstname()).isEqualTo(PropertyReader.readKey("booking.put.firstname"));
        assertThat(booking1.getLastname()).isEqualTo(PropertyReader.readKey("booking.put.lastname"));



    }

    @Test(groups = "integration", priority = 4)
    @Owner("Promode")
    @Description("TC#INT1 - Step 4. Delete the Booking by ID")
    public void testDeleteBookingById(ITestContext iTestContext) {

        String token = (String) iTestContext.getAttribute("token");
        Assert.assertTrue(true);

        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathDELETE);

        requestSpecification.basePath(basePathDELETE).cookie("token", token);
        ValidatableResponse validatableResponse = RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();
        validatableResponse.statusCode(201);


    }

}
