package com.example.networkintestapp.stacks;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.example.networkintestapp.Constant;
import com.example.networkintestapp.ResponseToUi;
import com.example.networkintestapp.SettingsActivity;
import com.example.networkintestapp.stacks.cronetutils.MyUrlRequestCallback;
import com.google.android.gms.net.CronetProviderInstaller;

import org.chromium.net.CronetEngine;
import org.chromium.net.CronetException;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;

public class CronetStack {

    private Context mContext;
    private Executor executor;
    private boolean useHttps;
    private boolean useProxy;

    public CronetStack (Context context, Executor thisExecutor, boolean doesHttps,
                        boolean doesProxy) {
        mContext = context;
        executor = thisExecutor;
        useHttps = doesHttps;
        useProxy = doesProxy;
    }

    public void doRequest(String TAG, ResponseToUi model) throws IOException {

        CronetProviderInstaller.installProvider(mContext);

        CronetEngine.Builder myBuilder = new CronetEngine.Builder(mContext).enableHttpCache(
                CronetEngine.Builder.HTTP_CACHE_DISABLED, 0);
        CronetEngine cronetEngine = myBuilder.build();

        final SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(mContext);

        String url = sharedPref.getString(SettingsActivity.KEY_HTTP_URL,
                Constant.DEFAULT_URL_HTTP);
        if (useHttps) url = sharedPref.getString(SettingsActivity.KEY_HTTPS_URL,
                Constant.DEFAULT_URL_HTTPS);

        UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder(url,
                new MyUrlRequestCallback(TAG, model), executor);
        UrlRequest request = requestBuilder.build();

        request.start();
    }
}
