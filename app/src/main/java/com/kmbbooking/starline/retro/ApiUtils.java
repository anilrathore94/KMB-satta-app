package com.kmbbooking.starline.retro;


public class ApiUtils {
    public static final String BASE_URL = "https://ab2software.com/satta/";//http://kubermatkabooking.tech/";

    private ApiUtils() {
    }

    public static RetrofitInterface getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(RetrofitInterface.class);
    }
}