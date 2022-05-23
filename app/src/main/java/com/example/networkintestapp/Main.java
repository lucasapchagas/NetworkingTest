package com.example.networkintestapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.google.android.material.snackbar.Snackbar;

public class Main extends AppCompatActivity {

    private static final String CHANNEL_ID = "NETWORKINGTEST";
    public Switch switchHttps;
    public Switch switchDisrespectAndroid;
    public RadioGroup radioNetworkStack;
    public ToggleButton buttonTerminalToggle;
    private ResponseToUi model;
    private String TAG = this.getClass().getName();
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchHttps = findViewById(R.id.switchHttps);
        switchDisrespectAndroid = findViewById(R.id.switchDisrespectAndroid);
        // TO-DO implement custom proxy for the app
        //switchDisrespectAndroid.setEnabled(false);
        radioNetworkStack = findViewById(R.id.radioGroupNetworkStack);
        buttonTerminalToggle = findViewById(R.id.buttonTerminalToggle);
        for (int i = 0; i < radioNetworkStack.getChildCount(); i++) {
            radioNetworkStack.getChildAt(i).setId(i);
        }
        radioNetworkStack.getChildAt(0).performClick();
        Log.d(TAG, "Initialized app.");
        model = new ViewModelProvider(this).get(ResponseToUi.class);
        dataListener();
        buttonsListener();
        createNotificationChannel();
    }

    public void dataListener() {
        final Observer<String> responseMessageObserver = new Observer<String>() {
            @Override
            public void onChanged(String message) {
                //Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

                Snackbar snackbar =
                        Snackbar.make(findViewById(android.R.id.content), message,
                                Snackbar.LENGTH_SHORT).setAction("VIEW",
                                new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sendNotificationMessage(message);
                            }
                        });
                snackbar.show();
            }
        };
        model.getResponseMessage().observe(this, responseMessageObserver);
    }

    public void buttonsListener() {
        buttonTerminalToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final boolean switchHttpsState = switchHttps.isChecked();
                final boolean switchDisrespectAndroidState = switchDisrespectAndroid.isChecked();
                final int httpStack = radioNetworkStack.getCheckedRadioButtonId();

                if (isChecked) {
                    final TerminalThread terminalThreadClass = new TerminalThread(mContext, model,
                            switchHttpsState, switchDisrespectAndroidState, httpStack);
                    terminalThreadClass.run();
                    buttonsEnabledState(false);
                    return;
                }

                Log.d(TAG, "TerminalThread is over.");
                buttonsEnabledState(true);
            }
        });
    }

    public void buttonsEnabledState(boolean state) {
        switchHttps.setEnabled(state);
        // switchDisrespectAndroid.setEnabled(state);
        for (int i = 0; i < radioNetworkStack.getChildCount(); i++) {
             radioNetworkStack.getChildAt(i).setEnabled(state);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "NetworkingTestApp Channel";
            String description = "NetworkingTestApp notification channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendNotificationMessage(String message) {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Networking app test")
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
        notificationManager.notify(1, builder.build());
    }
}