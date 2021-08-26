package com.example.practice1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    CntHandler handler;
    TextView text;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new CntHandler();
        text = (TextView)findViewById(R.id.count);
        btn = (Button)findViewById(R.id.btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new CountThread();
                thread.start();
            }
        });



    }


    class CntHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            int num = bundle.getInt("count");
            text.setText("" + num);
        }
    }

    class CountThread extends Thread {

        @Override
        public void run() {
            for (int i = 10; 0 <= i; i--) {
                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("count", i);
                message.setData(bundle);
                handler.sendMessage(message);
                try {
                    this.sleep(1000);
                } catch (Exception e) {
                }
            }

        }

    }
}