package com.example.fcm_practice;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String TAG = "Firebase 메세지";
    String ChannelName = "one";
    String ChannelID = "Firbase";
    @Override
    public void onMessageReceived(@NonNull @org.jetbrains.annotations.NotNull RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getNotification() != null) {
            String messageBody = remoteMessage.getNotification().getTitle();
            String messageTitle= remoteMessage.getNotification().getBody();
            Log.d(TAG, "Message Notification Body: " + messageTitle );
            Log.d(TAG, "Message Notification Body: " + messageBody);

            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel channel = new NotificationChannel(ChannelID, ChannelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);



            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,ChannelID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setContentText(messageBody)
                    .setContentTitle(messageTitle)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,new Intent(this,MainActivity.class), PendingIntent.FLAG_ONE_SHOT);

            builder.setContentIntent(pendingIntent);

            notificationManager.notify(0,builder.build());



        }


    }

    @Override
    public void onNewToken(@NonNull @org.jetbrains.annotations.NotNull String s) {
        Log.d(TAG, "New Token " + s);
    }
}
