package com.example.simple_broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String CUSTOM_BROADCAST1 = "custom_broadcast1";
    private static final String CUSTOM_BROADCAST2 = "custom_broadcast2";
    private static final String CUSTOM_BROADCAST3 = "custom_broadcast3";
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(intent.getAction()){
                    case CUSTOM_BROADCAST1: showToast(CUSTOM_BROADCAST1); break;
                    case CUSTOM_BROADCAST2: showToast(CUSTOM_BROADCAST2);break;
                    case CUSTOM_BROADCAST3: showToast(CUSTOM_BROADCAST3);break;
                }

            }

        };



        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(CUSTOM_BROADCAST1);
                sendBroadcast(intent);
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(CUSTOM_BROADCAST2);
                sendBroadcast(intent);
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(CUSTOM_BROADCAST3);
                sendBroadcast(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(CUSTOM_BROADCAST1);
        filter.addAction(CUSTOM_BROADCAST2);
        filter.addAction(CUSTOM_BROADCAST3);
        registerReceiver(receiver,filter);
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


}