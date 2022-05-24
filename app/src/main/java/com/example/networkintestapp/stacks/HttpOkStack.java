package com.example.networkintestapp.stacks;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.example.networkintestapp.Constant;
import com.example.networkintestapp.ResponseToUi;
import com.example.networkintestapp.SettingsActivity;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpOkStack {

    private Context mContext;
    private boolean useHttps;
    private boolean useProxy;

    public HttpOkStack(Context context, boolean doesHttps, boolean doesProxy) {
        mContext = context;
        useHttps = doesHttps;
        useProxy = doesProxy;
    }

    public void doRequest(String TAG, ResponseToUi model) throws IOException {
        String url;
        OkHttpClient client;

        final SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(mContext);

        if (useProxy) {
            final String proxyHostname = sharedPref.getString(
                    SettingsActivity.KEY_PROXY_HOST,"");
            final Integer proxyPort = Integer.parseInt(sharedPref.getString(
                    SettingsActivity.KEY_PROXY_PORT, ""));

            Proxy proxy = new Proxy(Proxy.Type.HTTP,
                    new InetSocketAddress(proxyHostname, proxyPort));
            client = new OkHttpClient.Builder().proxy(proxy).build();
        } else {
            client = new OkHttpClient.Builder().build();
        }


        if (useHttps) {
            url = sharedPref.getString(SettingsActivity.KEY_HTTPS_URL,
                    Constant.DEFAULT_URL_HTTPS);
        } else {
            url = sharedPref.getString(SettingsActivity.KEY_HTTP_URL,
                    Constant.DEFAULT_URL_HTTP);
        }

        Request request = new Request.Builder().url(url)
                .header("Cache-control" ,"public,max-age=0").build();

        try (Response response = client.newCall(request).execute()) {

            int responseCode = response.code();
            Log.d(TAG, "HttpOkStack state: " + responseCode);

            String responseMessage = "HttpOkStack state: " + responseCode + " " + response.message();
            model.getResponseMessage().postValue(responseMessage);

            Log.d(TAG, "HttpOkStack state: " + response.isSuccessful());
        }
    }
}
