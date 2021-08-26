package com.example.broadcastreciever_practice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive 호출");

        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = parseSmsMessage(bundle);

        if(messages != null){
            if(messages.length >0){
                String sender = messages[0].getOriginatingAddress();
                String contents = messages[0].getMessageBody();

                Log.d(TAG, "sender : " + sender + ", contents :" + contents);
                sendToACtivity(context, sender, contents);

            }
        }

    }

    private void sendToACtivity(Context context, String sender, String contents) {
        Intent intent = new Intent(context, SmsActivity.class);
        intent.putExtra("sender", sender);
        intent.putExtra("contents", contents);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
        |Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);

    }


    private SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] objs = (Object[])bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];

        int smsCnt = objs.length;
        for(int i=0; i< smsCnt; i++){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[])objs[i],format);
            }
            else
             messages[i] = SmsMessage.createFromPdu((byte[])objs[i]);
        }
        return messages;
    }
}