package com.example.view_practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class CustomButton extends AppCompatButton {

    public CustomButton(@NonNull Context context) {
        super(context);

        init(context);
    }


    public CustomButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        setBackgroundColor(Color.CYAN);
        setTextColor(Color.BLACK);
        float textSize = getResources().getDimension(R.dimen.text_size);
        // px단위
        setTextSize(textSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("MyButton", "onDraw 호출");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("MyButton", "onTouchEvent 호출");

        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(Color.RED);
                break;
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.GREEN);
                break;
        }
        // 화면을 다시 그려준다.
        invalidate();

        return true;
    }
}
