package com.example.networkintestapp;



import androidx.annotation.IntDef;

import java.net.Proxy;
import java.net.SocketAddress;

public class Constant {

    public static final String DEFAULT_URL_HTTP = "a"; // http://www.pudim.com.br/
    public static final String DEFAULT_URL_HTTPS = "b"; // https://www.google.com/
    public static final String PROXY_HOSTNAME = "";
    public static final int PROXY_PORT = 0;

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
