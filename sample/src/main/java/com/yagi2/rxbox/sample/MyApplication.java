package com.yagi2.rxbox.sample;

import android.app.Application;

public class MyApplication extends Application {
    private static boolean tokenIsInvalid = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static boolean isTokenIsInvalid() {
        return tokenIsInvalid;
    }

    public static void setTokenIsInvalid(boolean tokenIsInvalid) {
        MyApplication.tokenIsInvalid = tokenIsInvalid;
    }
}
