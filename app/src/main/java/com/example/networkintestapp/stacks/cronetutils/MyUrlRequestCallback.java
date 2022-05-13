package com.example.networkintestapp.stacks.cronetutils;

import android.util.Log;

import com.example.networkintestapp.R;
import com.example.networkintestapp.ResponseToUi;

import org.chromium.net.CronetException;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;

import java.nio.ByteBuffer;

public class MyUrlRequestCallback extends UrlRequest.Callback {

    private String TAG;
    private ResponseToUi mModel;

    public MyUrlRequestCallback(String thisTag, ResponseToUi thisModel) {
        TAG = thisTag;
        mModel = thisModel;
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

        String responseMessage = responseCode + " " + info.getHttpStatusText();
        mModel.getResponseMessage().postValue(responseMessage);
        // Log.d(TAG, "CronetStack state: " + info.getHttpStatusText());

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
