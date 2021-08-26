package com.example.broadcastreciever_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SmsActivity extends AppCompatActivity {
    TextView textView1;
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        textView1 = findViewById(R.id.textView1);
        textView2= findViewById(R.id.textView2);

        Intent intent = getIntent();
        processIntent(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processIntent(intent);
    }

    private void processIntent(Intent intent) {
        if(intent != null){
           String sender = intent.getStringExtra("sender");
           String contents = intent.getStringExtra("contents");

           textView1.setText(sender);
           textView2.setText(contents);
        }
    }
}