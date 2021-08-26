package com.example.multiactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void button2(View view) {
        Intent intent2 = new Intent(this , Main2Activity.class);
        startActivity(intent2);
    }



    public void button3(View view) {
        Intent intent3 = new Intent(this , Main3Activity.class);
        startActivity(intent3);
    }
        }



