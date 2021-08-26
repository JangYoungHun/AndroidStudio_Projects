package com.example.playground;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    View l1, l2;
    GestureDetector detector;
    String name;
    EditText editText;
    Button saveBtn;
    TextView textView1;
    @Override
    protected void onDestroy() {
        super.onDestroy();

        showToast("OnDestroy 호출");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        l1 = (View) findViewById(R.id.layout1);
        l2 = (View) findViewById(R.id.layout2);

        editText = (EditText)findViewById(R.id.editText);
        saveBtn =(Button)findViewById(R.id.saveBtn);
        textView1 = (TextView)findViewById(R.id.textView1);
       // showToast("OnCreate 호출");

        if(saveBtn != null) {
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editText != null) {
                        name = editText.getText().toString();
                        showToast("저장 완료");

                    }
                }
            });
        }
        if(savedInstanceState != null){
            if(textView1 != null){
                name = savedInstanceState.getString("name");
                textView1.setText(name);
                showToast("저장 값 복원");
            }

        }

        detector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                Toast.makeText(MainActivity.this, "OnDown", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Toast.makeText(MainActivity.this, "OnScroll", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Toast.makeText(MainActivity.this, "OnLongPress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Toast.makeText(MainActivity.this, "OnFling"+ "x속도 : " + velocityX, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        l1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });
    }


    public void showToast(String str){
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
    }
}