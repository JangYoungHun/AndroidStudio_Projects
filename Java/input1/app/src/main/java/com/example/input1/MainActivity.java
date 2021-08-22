package com.example.input1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void aaCLICK(View target) {

        TextView textView = findViewById(R.id.textView);
        CheckBox checkBox = findViewById(R.id.checkBox);
        EditText editText = findViewById(R.id.input);

        boolean checked = checkBox.isChecked();

        if(checked)
            {textView.setText(editText.getText());}
        else
            {Toast.makeText(MainActivity.this , "체크박스 클릭" , Toast.LENGTH_SHORT).show();}




    }
}
