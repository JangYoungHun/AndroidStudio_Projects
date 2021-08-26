package com.example.backgroundmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button start_Btn, stop_Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_Btn = findViewById(R.id.start_Btn);
        stop_Btn = findViewById(R.id.stop_Btn);


        start_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startService(new Intent(getApplicationContext(), MusicService.class));
            }
        });
        stop_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          stopService(new Intent(getApplicationContext(), MusicService.class));
            }
        });
    }
}