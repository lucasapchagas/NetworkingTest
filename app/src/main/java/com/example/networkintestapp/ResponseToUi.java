package com.example.networkintestapp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ResponseToUi extends ViewModel {

    private MutableLiveData<String> responseMessage;
    private MutableLiveData<Integer> responseStack;

    public MutableLiveData<String> getResponseMessage() {
        if (responseMessage == null){
            responseMessage = new MutableLiveData<String>();
        }
        return responseMessage;
    }

    public MutableLiveData<Integer> getResponseStack() {
        if (responseStack == null){
            responseStack = new MutableLiveData<Integer>();
        }
        return responseStack;
    }
}
