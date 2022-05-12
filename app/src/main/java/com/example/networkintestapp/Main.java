package com.example.networkintestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.ToggleButton;

public class Main extends AppCompatActivity {

    public Switch switchHttps;
    public Switch switchDisrespectAndroid;
    public RadioGroup radioNetworkStack;
    public ToggleButton buttonTerminalToggle;
    private String TAG = this.getClass().getName();
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchHttps = findViewById(R.id.switchHttps);
        switchDisrespectAndroid = findViewById(R.id.switchDisrespectAndroid);
        radioNetworkStack = findViewById(R.id.radioGroupNetworkStack);
        buttonTerminalToggle = findViewById(R.id.buttonTerminalToggle);
        for (int i = 0; i < radioNetworkStack.getChildCount(); i++) {
            radioNetworkStack.getChildAt(i).setId(i);
        }
        radioNetworkStack.getChildAt(0).performClick();
        Log.d(TAG, "Programa inicializado.");
        buttonsListener();
    }

    public void buttonsListener() {
        buttonTerminalToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final boolean switchHttpsState = switchHttps.isChecked();
                final boolean switchDisrespectAndroidState = switchDisrespectAndroid.isChecked();
                final int httpStack = radioNetworkStack.getCheckedRadioButtonId();

                if (isChecked) {
                    final TerminalThread terminalThreadClass = new TerminalThread(mContext,
                            switchHttpsState, switchDisrespectAndroidState, httpStack);
                    terminalThreadClass.run();
                    buttonsEnabledState(false);
                    return;
                }

                Log.d(TAG, "TerminalThread finalizada.");
                buttonsEnabledState(true);
            }
        });
    }

    public void buttonsEnabledState(boolean state) {
        switchHttps.setEnabled(state);
        switchDisrespectAndroid.setEnabled(state);
        for (int i = 0; i < radioNetworkStack.getChildCount(); i++) {
             radioNetworkStack.getChildAt(i).setEnabled(state);
        }
    }
    public void createResponseDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}