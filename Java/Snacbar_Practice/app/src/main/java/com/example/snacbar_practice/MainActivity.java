package com.example.snacbar_practice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Snackbar snackbar =  Snackbar.make(v, "스낵바 입니다.", Snackbar.LENGTH_INDEFINITE);
               snackbar.setAction("확인", new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       snackbar.dismiss();
                   }
               });
               snackbar.show();
            }
        });

        Button button2 = (Button)findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage();
            }
        });
    }

    public void showMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("제목")
                .setMessage("안녕하세요?")
                .setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                  Toast.makeText(MainActivity.this, "Yes 누름", Toast.LENGTH_SHORT).show();
              }
                });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "No 누름", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog diolog =  builder.create();
        diolog.show();
    }
}