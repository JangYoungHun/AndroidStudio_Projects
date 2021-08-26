package com.example.service_practice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    private static final String TAG = "MyService" ;
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "MyService onCreate 호출");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "MyService onStartCommand 호출");
        if(intent != null){
            processCommand(intent);
        }

        return super.onStartCommand(intent, flags, startId);

    }

    private void processCommand(Intent intent) {
        String command = intent.getStringExtra("command");
        String name = intent.getStringExtra("name");
        Log.d(TAG, "command : " + command);
        Log.d(TAG, "name : " + name);

        try {
            Thread.sleep(5000);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Intent showIntent = new Intent(getApplicationContext(),MainActivity.class);
        showIntent.putExtra("command", command);
        showIntent.putExtra("name", name);
        showIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|
                Intent.FLAG_ACTIVITY_CLEAR_TOP|
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(showIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MyService onDestroy 호출");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }
}