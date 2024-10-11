package com.testingAcademy.Aserts;

import static junit.framework.Assert.assertEquals;

public class AssertActions {
    public void verifyResponseBody(String actual, String expected, String description) {
        assertEquals(actual, expected, description);
    }
}


