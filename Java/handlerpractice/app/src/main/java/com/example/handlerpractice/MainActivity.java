package com.example.handlerpractice;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int count = 1;
    private TextView textmain;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable(){
        @Override
            public void run(){
            textmain.setText(count+"");
            count++;

            handler.postDelayed(runnable, 1000);
            if(count >= 10) {
                handler.removeCallbacks(runnable);
            }
        }
    };

    public void buttonClick(View view){
        handler.post(runnable);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textmain = findViewById(R.id.textmain);



    }
}
