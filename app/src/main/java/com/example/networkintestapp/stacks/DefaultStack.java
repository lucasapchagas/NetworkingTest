package com.example.networkintestapp.stacks;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.example.networkintestapp.Constant;
import com.example.networkintestapp.ResponseToUi;
import com.example.networkintestapp.SettingsActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

public class DefaultStack {

    private boolean useHttps;
    private boolean useProxy;
    private static Context mContext;

    public DefaultStack(Context context, boolean doesHttps, boolean doesProxy) {
        mContext = context;
        useHttps = doesHttps;
        useProxy = doesProxy;
    }

    public void doRequest(String TAG, ResponseToUi model) throws IOException {

        try {

            final SharedPreferences sharedPref =
                    PreferenceManager.getDefaultSharedPreferences(mContext);

            URL url = new URL(sharedPref.getString(SettingsActivity.KEY_HTTP_URL,
                    Constant.DEFAULT_URL_HTTP));

            if (useHttps) url = new URL(sharedPref.getString(SettingsActivity.KEY_HTTPS_URL,
                    Constant.DEFAULT_URL_HTTPS));

            HttpURLConnection http;

            if (useProxy) {
                final String proxyHostname = sharedPref.getString(
                        SettingsActivity.KEY_PROXY_HOST,"");
                final Integer proxyPort = Integer.parseInt(sharedPref.getString(
                        SettingsActivity.KEY_PROXY_PORT, ""));

                Proxy proxy = new Proxy(Proxy.Type.HTTP,
                        new InetSocketAddress(proxyHostname, proxyPort));
                http = (HttpURLConnection)url.openConnection(proxy);
            }
            else {
                http = (HttpURLConnection)url.openConnection();
                http.setUseCaches(false);
            }

            int responseCode = http.getResponseCode();

            Log.d(TAG, "DefaultHttpStack state: " + responseCode);
            if ( responseCode >= 200 && responseCode <= 299) {
                Log.d(TAG, "DefaultHttpStack state: true");
            }

            String responseMessage = "DefaultHttpStack state: " + responseCode + " " + http.getResponseMessage();
            model.getResponseMessage().postValue(responseMessage);

            http.disconnect();
        } catch (MalformedURLException e) {
            throw e;
        }
    }
}
