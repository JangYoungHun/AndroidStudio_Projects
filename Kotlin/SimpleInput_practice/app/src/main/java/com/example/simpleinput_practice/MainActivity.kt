package com.example.simpleinput_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submit_Btn.setOnClickListener{
            var name:String = input1.text.toString()
            var mobile:String = input2.text.toString()

            Toast.makeText(applicationContext, "이름 : $name, 연락처 : $mobile", Toast.LENGTH_SHORT).show()

            textView.setText("이름 : $name, 연락처 : $mobile")
            println("안녕")

        }


    }
}