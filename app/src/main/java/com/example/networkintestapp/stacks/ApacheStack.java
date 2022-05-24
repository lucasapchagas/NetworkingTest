package com.example.networkintestapp.stacks;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.example.networkintestapp.Constant;
import com.example.networkintestapp.ResponseToUi;
import com.example.networkintestapp.SettingsActivity;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.message.BasicHeader;

import java.io.IOException;


public class ApacheStack {

    private Context mContext;
    private boolean useHttps;
    private boolean useProxy;


    public ApacheStack(Context context, boolean doesHttps, boolean doesProxy) {
        mContext = context;
        useHttps = doesHttps;
        useProxy = doesProxy;
    }

    public void doRequest(String TAG, ResponseToUi model) throws IOException {
        final SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(mContext);

        String url = sharedPref.getString(SettingsActivity.KEY_HTTP_URL,
                Constant.DEFAULT_URL_HTTP);
        if (useHttps) url = sharedPref.getString(SettingsActivity.KEY_HTTPS_URL,
                Constant.DEFAULT_URL_HTTPS);

        final CloseableHttpClient httpClient = HttpClients.createDefault();

        final HttpGet httpGet = new HttpGet(url);


        httpGet.setHeader(new BasicHeader("Pragma", "no-cache"));
        httpGet.setHeader(new BasicHeader("Cache-Control", "no-cache"));

        final CloseableHttpResponse response;

        if (useProxy) {
            final String proxyHostname = sharedPref.getString(
                    SettingsActivity.KEY_PROXY_HOST,"");
            final Integer proxyPort = Integer.parseInt(sharedPref.getString(
                    SettingsActivity.KEY_PROXY_PORT, ""));

            HttpHost proxy = new HttpHost(proxyHostname, proxyPort);
            response = httpClient.execute(proxy, httpGet);
        } else {
            response = httpClient.execute(httpGet);
        }

        try {
            int responseCode = response.getCode();
            Log.d(TAG, "ApacheStack state: " + responseCode);
            String responseMessage = "ApacheStack state: " + responseCode + " " + response.getReasonPhrase();
            if ( responseCode >= 200 && responseCode <= 299) {
                Log.d(TAG, "ApacheStack state: true");
                response.close();
                model.getResponseMessage().postValue(responseMessage);
            }
        } catch (Exception e) {
            response.close();
            throw e;
        }
    }
}
