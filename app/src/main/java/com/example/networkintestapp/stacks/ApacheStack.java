package com.example.networkintestapp.stacks;

import android.util.Log;

import com.example.networkintestapp.Constant;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;


public class ApacheStack {
    private boolean useHttps;
    private boolean useProxy;

    public ApacheStack(boolean doesHttps, boolean doesProxy) {
        useHttps = doesHttps;
        useProxy = doesProxy;
    }

    public boolean doRequest(String TAG) throws IOException {
        boolean success = false;

        String url = Constant.DEFAULT_URL_HTTP;
        if (useHttps) url = Constant.DEFAULT_URL_HTTPS;

        final CloseableHttpClient httpClient = HttpClients.createDefault();
        final HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = httpClient.execute(httpGet);
        try {
            int responseCode = response.getCode();
            Log.d(TAG, "ApacheStack state: " + responseCode);
            if ( responseCode >= 200 && responseCode <= 299) {
                Log.d(TAG, "ApacheStack state: true");
                response.close();
                success = true;
            }
        } catch (Exception e) {
            response.close();
            success = false;
        } finally {
            return success;
        }
    }
}
