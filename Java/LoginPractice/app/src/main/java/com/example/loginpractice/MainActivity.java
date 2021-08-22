package com.example.loginpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    Button loginBtn;
    EditText id;
    EditText password;
    String inputId;
    String inputPassword;

    final String validId = "1234@gmail.com";
    final String validPassword = "1234";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loginBtn = (Button) findViewById(R.id.loginBtn);
        id = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.password);

        loginBtn.setClickable(false);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputId = id.getText().toString();
                inputPassword = password.getText().toString();

                if(inputId.equals(validId) && inputPassword.equals(validPassword)){
                    Intent login = new Intent(MainActivity.this, LoginSuccess.class);
                    login.putExtra("id", validId);
                    login.putExtra("password", validPassword);
                    startActivity(login);
                }

                else
                    Toast.makeText(getApplicationContext(),"로그인 정보를 확인하세요", Toast.LENGTH_SHORT).show();
            }
        });
    }
}