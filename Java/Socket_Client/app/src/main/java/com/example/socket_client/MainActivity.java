package com.example.socket_client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    //mrjangsserver.ddns.net

    static final String address = "mrjangsserver.ddns.net";
    static final int port = 8888;
    private static final int READ_DATA_MSG = 1;
    Button sendBtn;
    EditText sendData_ET;
    Connect connect;
    TextView resultText;
    Button connectBtn;
    Button  disconnectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendBtn = (Button) findViewById(R.id.sendBtn);
        sendData_ET = (EditText) findViewById(R.id.sendData_ET);
        resultText = (TextView)findViewById(R.id.resultText);
        connectBtn = (Button) findViewById(R.id.connectBtn);
        disconnectBtn = (Button) findViewById(R.id.disconnectBtn);


        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connect == null || !connect.isAlive()){
                    connect = new Connect(address,port);
                        connect.start();
                    Toast.makeText(MainActivity.this, "서버 연결", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(MainActivity.this, "서버에 이미 연결중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        disconnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connect != null || connect.isAlive()) {
                    connect.closeSocket();
                    connect.setStop(true);
                    connect = null;
                    Toast.makeText(MainActivity.this, "서버 연결 해제", Toast.LENGTH_SHORT).show();
                }
                else if(connect == null || !connect.isAlive())
                    Toast.makeText(MainActivity.this, "서버 접속중이 아닙니다.", Toast.LENGTH_SHORT).show();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connect!=null && connect.serverSocket != null) {
                    String data = sendData_ET.getText().toString();
                    connect.setWrite(data);
                    Toast.makeText(MainActivity.this,  data +" : 전송 중", Toast.LENGTH_SHORT).show();
                }
                else
                  Toast.makeText(MainActivity.this, "서버 접속 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull @org.jetbrains.annotations.NotNull Message msg) {
            if(msg.what == READ_DATA_MSG){
                Bundle bundle = msg.getData();
                String data = bundle.getString("data");
                resultText.setText(data);
            };
        }
    };



    class Connect extends Thread{

        String address;
        int port;
        Socket serverSocket;
        DataOutputStream out;
        DataInputStream in;

        boolean isStop = false;
        boolean isWrite = false;
        String sendData = "";

        Connect(String address, int port){
            this.address = address;
            this.port = port;
        }

        public boolean createSocket(){
            try{
                serverSocket = new Socket(address, port);
                out = new DataOutputStream(serverSocket.getOutputStream());
                in = new DataInputStream(serverSocket.getInputStream());

                return true;
            }
            catch (Exception e){
                e.printStackTrace();

                return false;
            }
        }

        private void readData(){
            try {
                String str = "";
                if(in.available() > 0) {
                    str = in.readUTF();
                    if(str.equals("eixt") || str.equals("eixt")){
                        setStop(true);
                    }
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", str);
                    msg.setData(bundle);
                    msg.what = READ_DATA_MSG;
                    handler.sendMessage(msg);
                }

            }
            catch (Exception e){
                e.printStackTrace();

            }
        }

        void setWrite(String data){
            isWrite = true;
            sendData = data;
        }
        void writeData(){
            try {
                if(isWrite) {
                    out.writeUTF(sendData);
                    out.flush();
                    isWrite = false;
                    sendData="";
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        void closeSocket(){
            try {
                if(serverSocket != null)
                    serverSocket.close();
                if(out!= null)
                    out.close();
                if(in != null)
                    in.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        void setStop(boolean isStop){
            this.isStop = isStop;
        }
        @Override
        public void run() {
            if(this.createSocket()) {
                while (!isStop) {
                    readData();
                    if(isWrite)
                        this.writeData();
                }
            }
        }



    }



}