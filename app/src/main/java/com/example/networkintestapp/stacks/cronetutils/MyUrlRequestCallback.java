package com.example.networkintestapp.stacks.cronetutils;

import android.util.Log;

import org.chromium.net.CronetException;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;

import java.nio.ByteBuffer;

public class MyUrlRequestCallback extends UrlRequest.Callback {

    private String TAG;

    public MyUrlRequestCallback(String thisTag) {
        TAG = thisTag;
    }

    @Override
    public void onRedirectReceived(UrlRequest request, UrlResponseInfo info, String newLocationUrl) throws Exception {
        Log.d(TAG, "CronetStack state: Request redirected");
    }

    @Override
    public void onResponseStarted(UrlRequest request, UrlResponseInfo info) throws Exception {
        Log.d(TAG, "CronetStack state: Request started");

        int responseCode = info.getHttpStatusCode();

        Log.d(TAG, "CronetStack state: " + responseCode);

        if (responseCode >= 200 && responseCode <= 299) {
            Log.d(TAG, "CronetStack state: true");
        }
    }

    @Override
    public void onReadCompleted(UrlRequest request, UrlResponseInfo info, ByteBuffer byteBuffer) throws Exception {
        Log.d(TAG, "CronetStack state: request fully read");
    }

    @Override
    public void onSucceeded(UrlRequest request, UrlResponseInfo info) {
        Log.d(TAG, "CronetStack state: succeeded");
    }

    @Override
    public void onFailed(UrlRequest request, UrlResponseInfo info, CronetException error) {
        Log.d(TAG, "CronetStack state: error");
        Log.d(TAG, error.toString());
    }
}
