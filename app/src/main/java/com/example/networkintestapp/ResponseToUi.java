package com.example.networkintestapp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ResponseToUi extends ViewModel {

    private MutableLiveData<String> responseMessage;
    private MutableLiveData<Integer> responseCode;

    public MutableLiveData<String> getResponseMessage() {
        if (responseMessage == null){
            responseMessage = new MutableLiveData<String>();
        }
        return responseMessage;
    }

    public MutableLiveData<Integer> getResponseCode() {
        if (responseCode == null){
            responseCode = new MutableLiveData<Integer>();
        }
        return responseCode;
    }
}
