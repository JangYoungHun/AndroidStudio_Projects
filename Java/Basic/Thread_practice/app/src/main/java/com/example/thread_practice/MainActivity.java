package com.example.thread_practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
   // MyHandler handler;
    Handler handler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackGroundThread thread = new BackGroundThread();
                thread.start();
            }
        });

        //handler = new MyHandler();

    }

/*    class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            int value = bundle.getInt("value");
            textView.setText("value : " + value);
        }
    }
*/

    class BackGroundThread extends Thread{
        int value = 0;
        @Override
        public void run() {
            value = 0;
            for(int i=0; i<100; i++){
                try{
                    currentThread().sleep(1000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                value++;
              //  Log.d("BackGroundThread", "value : "+i);
              /*  Message message =  handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("value", i);
                message.setData(bundle);

                handler.sendMessage(message);
                */

               /*   handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("value : " + value);
                    }
                }); */

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("value : " + value);
                    }
                }, 5000);
            }
        }
    }



}