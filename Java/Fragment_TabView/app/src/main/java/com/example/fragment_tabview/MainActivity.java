package com.example.fragment_tabview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tab;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tab = findViewById(R.id.tab);
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        tab.addTab(tab.newTab().setText("1번"));
        tab.addTab(tab.newTab().setText("2번"));
        tab.addTab(tab.newTab().setText("3번"));

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment1).commit();


        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch(tab.getPosition()){
                    case 0 : fragment = fragment1;  break;
                    case 1 : fragment = fragment2;  break;
                    case 2 : fragment = fragment3;  break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}