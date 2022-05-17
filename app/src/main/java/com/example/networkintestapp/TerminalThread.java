package com.example.networkintestapp;

import android.content.Context;
import android.util.Log;

import com.example.networkintestapp.stacks.ApacheStack;
import com.example.networkintestapp.stacks.CronetStack;
import com.example.networkintestapp.stacks.DefaultStack;
import com.example.networkintestapp.stacks.HttpOkStack;

import java.security.InvalidParameterException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TerminalThread {

    private static boolean HttpsState;
    private static boolean DisrespectAndroidState;
    private static int HttpStack;
    private static String TAG = TerminalThread.class.getName();
    private static ResponseToUi mModel;
    private static Context mContext;

    public TerminalThread(Context context, ResponseToUi model, boolean switchHttpsState,
                          boolean switchDisrespectAndroidState, int httpStack) {

        mContext = context;
        mModel = model;
        HttpsState = switchHttpsState;
        DisrespectAndroidState = switchDisrespectAndroidState;
        HttpStack = httpStack;
    }

    public void run() {
        Log.d(TAG, "TerminalThread initialized.");
        Log.d(TAG, "HttpsState: " + HttpsState);
        Log.d(TAG, "DisrespectAndroidState: " + DisrespectAndroidState);
        Log.d(TAG, "HttpStack: " + HttpStack);
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();

        try {
            switch (HttpStack) {
                case (Constant.Stack.DEFAULT):
                    executorService1.execute(new Runnable() {
                        @Override
                        public void run() {
                            DefaultStack defaultStack = new DefaultStack(HttpsState,
                                    false);
                            try {
                                defaultStack.doRequest(TAG, mModel);
                            } catch (Exception e) {
                                Log.d(TAG, e.toString());
                                mModel.getResponseMessage().postValue(TAG + " " + e.toString());
                            }
                        }
                    });
                    break;
                case (Constant.Stack.OKHTTP):
                    executorService1.execute(new Runnable() {
                        @Override
                        public void run() {
                            HttpOkStack httpOkStack = new HttpOkStack(mContext, HttpsState,
                                    false);
                            try {
                                httpOkStack.doRequest(TAG, mModel);
                            } catch (Exception e) {
                                Log.d(TAG, e.toString());
                                mModel.getResponseMessage().postValue(TAG + " " + e.toString());
                            }
                        }
                    });
                    break;
                case (Constant.Stack.CRONET):
                    CronetStack cronetStack = new CronetStack(mContext, executorService1,
                            HttpsState, false);
                    try {
                        cronetStack.doRequest(TAG, mModel);
                    } catch (Exception e) {
                        Log.d(TAG, e.toString());
                        mModel.getResponseMessage().postValue(TAG + " " + e.toString());
                    }
                    break;
                case (Constant.Stack.APACHE):
                    executorService1.execute(new Runnable() {
                        @Override
                        public void run() {
                            ApacheStack apacheStack = new ApacheStack(HttpsState, false);
                            try {
                                apacheStack.doRequest(TAG, mModel);
                            } catch (Exception e) {
                                Log.d(TAG, e.toString());
                                mModel.getResponseMessage().postValue(TAG + " " + e.toString());
                            }
                        }
                    });
                    break;
                default:
                    throw new InvalidParameterException();
            }
        } catch (InvalidParameterException e) {
            Log.e(TAG, e.toString());
        }
    }
}