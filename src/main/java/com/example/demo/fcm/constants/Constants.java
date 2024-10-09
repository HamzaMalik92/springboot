package com.example.demo.fcm.constants;

import com.example.demo.fcm.exception.UtilityClassInitiateException;
import lombok.experimental.UtilityClass;

public final class Constants {
//    Utility classes should not have public constructors - make private instead
    private Constants() {}
    public static final String FCM_URL_TEMPLATE = "https://fcm.googleapis.com/v1/projects/%s/messages:send";
    public static final String FCM_PROJECT_NAME = "expense-tracker-f1330";
    public static final String FCM_SERVICE_JSON_PATH = "src/main/resources/assets/json/google-services.json";
}
