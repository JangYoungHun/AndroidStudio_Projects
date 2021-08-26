package com.example.bottom_navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomView;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomView = findViewById(R.id.bottomView);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();


        getSupportFragmentManager().beginTransaction().replace(R.id.containerView,fragment1).commit();


        bottomView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){

                    case R.id.menu1 :     getSupportFragmentManager().beginTransaction().replace(R.id.containerView,fragment1).commit(); break;
                    case R.id.menu2 :     getSupportFragmentManager().beginTransaction().replace(R.id.containerView,fragment2).commit(); break;
                    case R.id.menu3 :     getSupportFragmentManager().beginTransaction().replace(R.id.containerView,fragment3).commit(); break;
                    default: break;
                }
                return false;
            }
        });



    }



}