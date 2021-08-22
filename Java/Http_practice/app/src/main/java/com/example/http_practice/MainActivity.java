package com.example.http_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    ScrollView scrollView;
    TextView textView;
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String urlStr = editText.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        requestUrl(urlStr);
                    }
                }).start();
            }
        });

    }

    private void requestUrl(String urlStr) {
        try {
            StringBuilder sb = new StringBuilder();
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if(connection != null){
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                int resqCode = connection.getResponseCode();

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;
                while(true){
                    line = br.readLine();
                    if(line == null)
                        break;

                    else
                        sb.append(line+ "\n");
                }
                br.close();
                connection.disconnect();
            }

            println(sb.toString());

         }
        catch (Exception e){}
    }

    private void println(String data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.append(data+ "\n");
            }
        });
    }
}