package com.example.networkintestapp.stacks;

import android.content.Context;
import android.util.Log;

import com.example.networkintestapp.Constant;
import com.example.networkintestapp.ResponseToUi;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpOkStack {

    private Context context;
    private boolean useHttps;
    private boolean useProxy;
    private OkHttpClient client = new OkHttpClient();

    public HttpOkStack(Context instanceContext, boolean doesHttps, boolean doesProxy) {
        context = instanceContext;
        useHttps = doesHttps;
        useProxy = doesProxy;
    }

    public void doRequest(String TAG, ResponseToUi model) throws IOException {
        String url;

        if (useHttps) {
            url = Constant.DEFAULT_URL_HTTPS;
        } else {
            url = Constant.DEFAULT_URL_HTTP;
        }

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {

            int responseCode = response.code();
            Log.d(TAG, "HttpOkStack state: " + responseCode);

            String responseMessage = responseCode + " " + response.message();
            model.getResponseMessage().postValue(responseMessage);

            Log.d(TAG, "HttpOkStack state: " + response.isSuccessful());
        }
    }
}
