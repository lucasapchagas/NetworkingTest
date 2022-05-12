package com.example.networkintestapp;

import androidx.annotation.IntDef;

public class Constant {

    public static final String DEFAULT_URL_HTTP = "http://www.pudim.com.br/";
    public static final String DEFAULT_URL_HTTPS = "https://www.google.com/";

    @IntDef({
            Stack.DEFAULT,
            Stack.OKHTTP,
            Stack.CRONET,
            Stack.APACHE
    })
    public @interface Stack {
        int DEFAULT = 0;
        int OKHTTP = 1;
        int CRONET = 2;
        int APACHE = 3;
    }

}
