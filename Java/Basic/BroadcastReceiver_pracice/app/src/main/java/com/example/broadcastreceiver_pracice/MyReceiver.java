package com.example.broadcastreceiver_pracice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      if(Intent.ACTION_POWER_CONNECTED.equals(intent.getAction())){
          Toast.makeText(context,"Bradcast 방송 중 ", Toast.LENGTH_SHORT).show();
      }
    }
}