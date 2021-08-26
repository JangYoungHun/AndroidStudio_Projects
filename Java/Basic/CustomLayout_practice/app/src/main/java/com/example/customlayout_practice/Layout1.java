package com.example.customlayout_practice;

import android.content.Context;
import android.media.Image;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Layout1 extends LinearLayout {

    ImageView imageView;
    TextView textView1;
    TextView textView2;

    public Layout1(Context context) {
        super(context);

        init(context);
    }

    public Layout1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout1, this,true);

        imageView = findViewById(R.id.imageView);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);

    }

    public void setImage(int srcImage){
        imageView.setImageResource(srcImage);
    }

    public void setName(String name){
        textView1.setText(name);
    }

    public void setContact(String contact){
        textView2.setText(contact);
    }
}
