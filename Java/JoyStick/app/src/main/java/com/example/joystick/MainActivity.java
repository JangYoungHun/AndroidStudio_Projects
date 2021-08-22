package com.example.joystick;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View stick = findViewById(R.id.stick);

        stick.setOnTouchListener(new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float curX = event.getRawX();
            float curY = event.getRawY();

            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    Log.d("viewTest" ,"클릭" );
                   return true;
                }
                case MotionEvent.ACTION_MOVE: {

                    Log.d("viewTest" ,"이동중");

                        v.setX(event.getX() + v.getX() - (v.getWidth() /2));
                        v.setY(event.getY()+ v.getY() - (v.getHeight() /2));

                    return true;
                }
                case MotionEvent.ACTION_UP: {
                    Log.d("viewTest" ,"클릭 끝");
                    Display display = getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    v.setX( (size.x/2) - (v.getWidth() /2));
                    v.setY( (size.y/2) - (v.getHeight() /2));
                    return false;
                }
                default:
                    return false;

            }
 }
        } );


    } // onCreate
}