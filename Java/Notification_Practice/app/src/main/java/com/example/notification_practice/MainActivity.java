package com.example.notification_practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button createBtn;
    Button deleteBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createBtn = (Button)findViewById(R.id.createBtn);
        deleteBtn = (Button)findViewById(R.id.deleteBtn);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNotification();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNotification();
            }
        });

    }

    public void createNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("알림 제목");
        builder.setContentText("알림 내용");
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(new NotificationChannel("default","default",NotificationManager.IMPORTANCE_DEFAULT));
        notificationManager.notify(0,builder.build());

    }


    public void deleteNotification(){
        NotificationManagerCompat.from(this).cancel(0);

    }



}