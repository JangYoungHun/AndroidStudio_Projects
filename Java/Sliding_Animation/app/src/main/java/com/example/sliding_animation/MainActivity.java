package com.example.sliding_animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import java.nio.file.Files;

public class MainActivity extends AppCompatActivity {
    Animation tr_left;
    Animation tr_right;
    LinearLayout page;
    Button button;

    boolean isPageOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        page = findViewById(R.id.page);
        tr_left = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        tr_right= AnimationUtils.loadAnimation(this, R.anim.translate_right);
        button = findViewById(R.id.button);

        SlidingAnimationListener listener = new SlidingAnimationListener();
        tr_left.setAnimationListener(listener);
        tr_right.setAnimationListener(listener);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPageOpen){
                    page.startAnimation(tr_right);
                }
                else {
                    page.setVisibility(View.VISIBLE);
                    page.startAnimation(tr_left);
                }
            }
        });
    }

    class SlidingAnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if(isPageOpen){
                page.setVisibility(View.INVISIBLE);
                button.setText("열기");
                isPageOpen = false;
            }
            else{
                page.setVisibility(View.VISIBLE);
                button.setText("닫기");
                isPageOpen = true;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}