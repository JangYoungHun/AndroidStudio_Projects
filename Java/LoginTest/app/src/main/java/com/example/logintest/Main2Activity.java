package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        TextView textViewId = findViewById(R.id.idGet);
        TextView textViewpwd = findViewById(R.id.passwordGet);

        Intent intent = getIntent();

        String id =intent.getStringExtra("입력한 아이디");
        String pwd =intent.getStringExtra("입력한 비밀번호");
        textViewId.setText(String.valueOf(id));
        textViewpwd.setText(String.valueOf(pwd));
    }
}