package com.example.networkintestapp.stacks;

import android.util.Log;

import com.example.networkintestapp.Constant;
import com.example.networkintestapp.ResponseToUi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DefaultStack {

    private boolean useHttps;
    private boolean useProxy;

    public DefaultStack(boolean doesHttps, boolean doesProxy) {
        useHttps = doesHttps;
        useProxy = doesProxy;
    }

    public void doRequest(String TAG, ResponseToUi model) throws IOException {

        try {
            URL url = new URL(Constant.DEFAULT_URL_HTTP);

            if (useHttps) url = new URL(Constant.DEFAULT_URL_HTTPS);

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            int responseCode = http.getResponseCode();

            Log.d(TAG, "DefaultHttpStack state: " + responseCode);
            if ( responseCode >= 200 && responseCode <= 299) {
                Log.d(TAG, "DefaultHttpStack state: true");
            }

            String responseMessage = "DefaultHttpStack state: " + responseCode + " " + http.getResponseMessage();
            model.getResponseMessage().postValue(responseMessage);

            http.disconnect();
        } catch (MalformedURLException e) {
            Log.d(TAG,"Default HTTP Stack, invalid URL.");
        }
    }
}
