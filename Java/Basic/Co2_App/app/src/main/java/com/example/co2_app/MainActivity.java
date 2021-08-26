package com.example.co2_app;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {



    BluetoothAdapter bluetoothAdapter;
    Set<BluetoothDevice> bluetoothDevices;
    List<String> list ;
    BluetoothDevice pairedBluetoothDevice;
    BluetoothSocket bluetoothSocket;
    Handler Bthandler;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    final static int BT_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    ConnectThread connectThread;

    Button sendBtn;
    Button Btonoff ;

    EditText sendMsg ;
    TextView Bt_Msg;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            //블루투스 on : BT_ENABLE
            case BT_ENABLE:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(getApplicationContext(), "블루투스 켜짐" , Toast.LENGTH_LONG).show();
                    //디바이스 선택함수 호출
                    pairing();
                }
                else if(resultCode == RESULT_CANCELED){
                    //취소시 동작
                    Toast.makeText(getApplicationContext(), "취소" , Toast.LENGTH_LONG).show();
                }
                break;

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String str;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendBtn = (Button)findViewById(R.id.sendBtn);
        Btonoff = (Button)findViewById(R.id.BluetoothBtn);
        sendMsg = (EditText)findViewById(R.id.sendMsg);
        Bt_Msg = (TextView) findViewById(R.id.Bt_Msg);

        sendBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(bluetoothSocket != null) {
                    connectThread.write(sendMsg.getText().toString());
                }
                else
                    Toast.makeText(getApplicationContext(), "연결이 되지 않았습니다", Toast.LENGTH_LONG).show();
            }
        }  );

        Btonoff.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


                if(bluetoothAdapter == null)
                {
                    // 블루투스 지원 안할 때
                    Toast.makeText(getApplicationContext(), "블루투스 지원 하지 않는 기기입니다." , Toast.LENGTH_LONG).show();
                }
                else {
                    // 블루투스 지원 할 때
                    if(bluetoothAdapter.isEnabled()) {
                        //블루투스가 켜져있을 때
                        Toast.makeText(getApplicationContext(), "블루투스가 켜져있습니다." , Toast.LENGTH_LONG).show();
                        //연결함수 실행
                        pairing();
                    }
                    else{
                        //블루투스가 꺼져있을 때
                        Toast.makeText(getApplicationContext(), "블루투스가 꺼져있습니다." , Toast.LENGTH_LONG).show();
                        // 블루투스 켜는 다이얼로그 발생시키기
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        // 1 은 리퀘스트 코드로 onActivityResult에 전달된다.      블루투스 on : BT_ENABLE
                        startActivityForResult(intent,BT_ENABLE);
                    }
                }



            }
        }  );





        Bthandler = new Handler(){
            public void handleMessage(android.os.Message msg){
                if(msg.what == BT_MESSAGE_READ){
                    String readMessage = null;
                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
                        Bt_Msg.setText(readMessage);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }

                }
            }

        };




    }

    void pairing(){
        if(bluetoothAdapter.isEnabled())
        {
            bluetoothDevices = bluetoothAdapter.getBondedDevices();
            if(bluetoothDevices.size() > 0)
            {  // 페어링 목록이 1개 이상이라면
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("장치 목록");
                list = new ArrayList<String>();

                for(BluetoothDevice device : bluetoothDevices)
                {
                    list.add(device.getName());
                }

                final CharSequence[] nameList = list.toArray(new CharSequence[bluetoothDevices.size()]);

                builder.setItems(nameList, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        connectSelectedDevice(nameList[item].toString());

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
            else
            { // 페어링 가능 기기가 0개다
                Toast.makeText(getApplicationContext(), "페어링 가능 기기가 없습니다.", Toast.LENGTH_LONG).show();
            }
        }
        else{
            // 블루투스가 off  라면
            Toast.makeText(getApplicationContext(), "블루투스가 꺼져있습니다.", Toast.LENGTH_LONG).show();
        }

    }

    private void connectSelectedDevice(String deviceName) {
        for(BluetoothDevice devices : bluetoothDevices){
            if(deviceName.equals(devices.getName()))
            {
                pairedBluetoothDevice = devices;
            }
        }
        try {
            bluetoothSocket = pairedBluetoothDevice.createInsecureRfcommSocketToServiceRecord(BT_UUID);

            bluetoothSocket.connect();
            if(bluetoothSocket.isConnected()) {
                Toast.makeText(getApplicationContext(), "연결 완료.", Toast.LENGTH_LONG).show();
            }
            // Thread 실행 부분

            connectThread = new ConnectThread(bluetoothSocket);
            connectThread.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }


    private class ConnectThread extends Thread {

        private BluetoothSocket socket;
        private InputStream input;
        private OutputStream output;

        public ConnectThread(BluetoothSocket socket) {

            try {
                this.socket = socket;
                input = socket.getInputStream();
                output = socket.getOutputStream();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "소켓연결줄 오류 발생", Toast.LENGTH_LONG).show();
            }

        }

        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
            while (true) {
                try {

                     // 읽을 수 있는 바이트수
                    if ( input.available() > 0) // 읽을 수 있는게 있다면
                    {
                        Thread.sleep(100);
                        bytes = input.available();
                        bytes = input.read(buffer, 0, bytes); // buuffer에 저장하고 읽은 바이트수 반환
                        Bthandler.obtainMessage(BT_MESSAGE_READ, bytes, -1,buffer).sendToTarget();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "데이터 수신 중 오류발생", Toast.LENGTH_LONG).show();
                } 
            }
        }

        public void write(String str){
            if(socket != null) {
                byte[] buffer = new byte[1024];
                try {
                    Thread.sleep(100);
                    buffer = str.getBytes();
                    output.write(buffer);
                    output.flush();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "데이터 전송 중 오류 발생", Toast.LENGTH_LONG).show();
                }
            }
        }

        public void disconnectSocket()
        {
            try {
                socket.close();
            }
            catch(Exception e){
                Toast.makeText(getApplicationContext(), "소켓 해제 중 오류 발생", Toast.LENGTH_LONG).show();
            }

        }
    }
}

