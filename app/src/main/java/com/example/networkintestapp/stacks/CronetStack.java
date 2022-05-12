package com.example.networkintestapp.stacks;

import android.content.Context;
import android.util.Log;

import com.example.networkintestapp.Constant;
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

    private Context context;
    private Executor executor;
    private boolean useHttps;
    private boolean useProxy;

    public CronetStack (Context instanceContext, Executor thisExecutor, boolean doesHttps,
                        boolean doesProxy) {
        context = instanceContext;
        executor = thisExecutor;
        useHttps = doesHttps;
        useProxy = doesProxy;
    }

    public void doRequest(String TAG) throws IOException {

        CronetProviderInstaller.installProvider(context);

        CronetEngine.Builder myBuilder = new CronetEngine.Builder(context);
        CronetEngine cronetEngine = myBuilder.build();

        String url = Constant.DEFAULT_URL_HTTP;
        if (useHttps) url = Constant.DEFAULT_URL_HTTPS;

        UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder(url,
                new MyUrlRequestCallback(TAG), executor);
        UrlRequest request = requestBuilder.build();

        request.start();
    }
}
