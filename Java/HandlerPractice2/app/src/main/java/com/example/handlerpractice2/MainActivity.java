package com.example.handlerpractice2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int count = 0;
    private Handler handler = new Handler();
    private TextView textMain;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            textMain.setText(count+"");

            count++;

            if(count ==10)
            {handler.removeCallbacks(runnable);}

            else{
            handler.postDelayed(this, 1000);}
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textMain = findViewById(R.id.textMain);
    }


    public void startButton(View view) {
        handler.post(runnable);
    }

}
