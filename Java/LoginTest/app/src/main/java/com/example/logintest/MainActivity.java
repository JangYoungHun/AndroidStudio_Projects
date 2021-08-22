package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.logintest.Main2Activity;
import com.example.logintest.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }





    public void loginButton(View view) {
        EditText editTextId = findViewById(R.id.id);
        EditText editTextPassword = findViewById(R.id.password);

        String id = editTextId.getText().toString();
        String password = editTextPassword.getText().toString();
        Intent intentLogin = new Intent(this , Main2Activity.class);
        intentLogin.putExtra("입력한 아이디", id);
        intentLogin.putExtra("입력한 비밀번호", password);

        startActivity(intentLogin);

    }
}
