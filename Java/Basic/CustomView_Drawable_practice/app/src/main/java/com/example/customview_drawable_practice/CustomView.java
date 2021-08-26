package com.example.customview_drawable_practice;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class CustomView extends View {

    ShapeDrawable upperDrawable;
    ShapeDrawable lowerDrawable;


    public CustomView(Context context) {
        super(context);
        init(context);
    }


    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        Resources resources = getResources();
        int blackColor  =resources.getColor(R.color.color01);
        int grayColor  =resources.getColor(R.color.color02);
        int darkGrayColor  =resources.getColor(R.color.color03);

        upperDrawable = new ShapeDrawable();
        RectShape rectShape = new RectShape();
        rectShape.resize(width, height*2/3);
        upperDrawable.setShape(rectShape);
        upperDrawable.setBounds(0,0,width,height*2/3);

        LinearGradient gradient = new LinearGradient(0,0,0,height*2/3,grayColor,blackColor, Shader.TileMode.CLAMP);
        Paint paint = upperDrawable.getPaint();
        paint.setShader(gradient);

        lowerDrawable = new ShapeDrawable();

        RectShape rect2 = new RectShape();
        rect2.resize(width, height*1/3);
        lowerDrawable.setShape(rect2);
        lowerDrawable.setBounds(0,height*2/3,width,height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        upperDrawable.draw(canvas);
        lowerDrawable.draw(canvas);
    }
}
