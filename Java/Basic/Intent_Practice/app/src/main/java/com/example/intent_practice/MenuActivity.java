package com.example.intent_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button button = (Button)findViewById(R.id.button);

        Intent getIntent = getIntent();
        String str = getIntent.getStringExtra("key");
        if(str != null){
            Toast.makeText(this,"수신" + str, Toast.LENGTH_SHORT).show();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("key", "Data 받아랏!");
                setResult(111, intent);
                finish();
            }
        });
    }



}