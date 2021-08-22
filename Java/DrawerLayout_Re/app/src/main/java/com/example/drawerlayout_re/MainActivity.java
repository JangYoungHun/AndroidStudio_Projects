package com.example.drawerlayout_re;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    View drawerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerView = findViewById(R.id.drawerView);
        drawerLayout = findViewById(R.id.drawerLayout);
        Button open_Btn = findViewById(R.id.openBtn);

        DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull @org.jetbrains.annotations.NotNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull @org.jetbrains.annotations.NotNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull @org.jetbrains.annotations.NotNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        };

        drawerLayout.addDrawerListener(listener);

        open_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            drawerLayout.openDrawer(drawerView);
            }
        });
    }
}