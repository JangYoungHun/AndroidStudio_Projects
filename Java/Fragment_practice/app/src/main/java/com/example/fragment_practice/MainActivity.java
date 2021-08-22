package com.example.fragment_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    Fragment1 frag1;
    Fragment2 frag2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frag1 = (Fragment1)getSupportFragmentManager().findFragmentById(R.id.fragment1);
        frag2 = new Fragment2();


    }


    public void onFragmentChanged(int index){

        if(index == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.containerView,frag1).commit();
        }
        else if(index == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.containerView, frag2).commit();
        }


    }
}