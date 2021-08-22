package com.example.changescreenorientation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showToast("OnCreate");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showToast("OnDestroy");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            showToast("가로 화면");
        }
        else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            showToast("세로 화면");
        }
    }

    void showToast(String data){
        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
    }
}