package com.example.animation_pratice;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView image1 = (ImageView)findViewById(R.id.image1);
        ImageView image2 = (ImageView)findViewById(R.id.image2);
        ImageView image3 = (ImageView)findViewById(R.id.image3);
        Button button1 = (Button)findViewById(R.id.button1);
        Button button2 = (Button)findViewById(R.id.button2);
        Button button3 = (Button)findViewById(R.id.button3);




        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjectAnimator anim1 = ObjectAnimator.ofFloat(image1,"translationX",200f);
                anim1.setDuration(2000);
                anim1.start();
            }
        });




        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjectAnimator anim2 = ObjectAnimator.ofFloat(image2, "translationY", 200f);
                anim2.setDuration(2000);
                anim2.start();
                }
        });



        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_anim3);
                image3.startAnimation(anim);

            }
        });



    }
}