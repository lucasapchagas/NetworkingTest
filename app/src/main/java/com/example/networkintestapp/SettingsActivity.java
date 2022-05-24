package com.example.networkintestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    public static String KEY_HTTP_URL = "value_http_url";
    public static String KEY_HTTPS_URL = "value_https_url";
    public static String KEY_PROXY_HOST = "value_proxy_hostname";
    public static String KEY_PROXY_PORT = "value_proxy_port";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}