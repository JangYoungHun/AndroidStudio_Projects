package com.example.backkeyexit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;




public class MainActivity extends AppCompatActivity {
    private BackKeyClick backKeyClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backKeyClick = new BackKeyClick(this);

        }
    public void onBackPressed(){
        backKeyClick.onBackPressd();


    }

    }


