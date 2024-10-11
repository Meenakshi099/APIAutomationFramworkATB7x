package com.testingAcademy.Modules;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.testingAcademy.Modules.Pojos.*;

public class PayloadManager {
    //ser & deser
    public static String createPayloadBookingAsString() {
        booking booking = new booking();
        booking.setFirstname("James");
        booking.setLastname("Brown");
        booking.setTotalprice(111);
        booking.setDepositpaid(true);

        BookingDates bookingdates = new BookingDates();
        bookingdates.setCheckin("2024-02-01");
        bookingdates.setCheckout("2024-02-01");

        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        System.out.println(booking);
        // Java Object -> JSON String (byteStream) - Serlization
        Gson gson = new Gson();
        String jsonStringpayload = gson.toJson(booking);
        System.out.println(jsonStringpayload);
        return jsonStringpayload;
    }

    public String createPayloadBookingAsStringFaker() {
        Faker faker = new Faker();
        booking booking = new booking();
        booking.setFirstname(faker.name().firstName());
        booking.setLastname(faker.name().lastName());
        booking.setTotalprice(faker.random().nextInt(1000));
        booking.setDepositpaid(true);

        BookingDates bookingdates = new BookingDates();
        bookingdates.setCheckin("2024-02-01");
        bookingdates.setCheckout("2024-02-01");

        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        System.out.println(booking);
        // Java Object -> JSON String (byteStream) - Serlization
        Gson gson = new Gson();
        String jsonStringpayload = gson.toJson(booking);
        System.out.println(jsonStringpayload);
        return jsonStringpayload;
    }

    public static bookingResponse bookingResponseJava(String responseString) {
        Gson gson = new Gson();
        bookingResponse bookingResponse = gson.fromJson(responseString, bookingResponse.class);
        return bookingResponse;
    }

    public String setAuthPayload() {
        Auth auth = new Auth();
        auth.setUsername("admin");
        auth.setPassword("password123");
        Gson gson = new Gson();
        String jsonPayloadString = gson.toJson(auth);
        //String payload = gson.toJson(auth)
        System.out.println("Payload set to the -> " + jsonPayloadString);
        return jsonPayloadString;

    }

    public String getTokenFromJSON(String tokenResponse) {
        Gson gson = new Gson();
        TokenResponse tokenResponse1 = gson.fromJson(tokenResponse, TokenResponse.class);
        return tokenResponse1.getToken();
    }
    // get Token


    // get Booking ID
    public static booking getResponseFromJSON(String getResponse) {
        Gson gson = new Gson();
        // Response ( JSON) ->  Object TokenResponse
        // Deserialization
        booking booking1 = gson.fromJson(getResponse, booking.class);
        return booking1;
    }

    public static booking fullUpdatePayloadAsString() {
        booking booking1 = new booking();
        booking1.setFirstname("Pramod");
        booking1.setLastname("Dutta");
        booking1.setTotalprice(112);
        booking1.setDepositpaid(true);

        BookingDates bookingdates = new BookingDates();
        bookingdates.setCheckin("2024-02-01");
        bookingdates.setCheckout("2024-02-05");
        booking1.setBookingdates(bookingdates);
        booking1.setAdditionalneeds("Breakfast");
         return booking1;


    }
}

