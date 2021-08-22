package com.example.loginpractice;


import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginSuccess extends AppCompatActivity {
    EditText id;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_success);

        id = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.password);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id.setText(bundle.getString("id"));
        password.setText(bundle.getString("password"));
    }
}