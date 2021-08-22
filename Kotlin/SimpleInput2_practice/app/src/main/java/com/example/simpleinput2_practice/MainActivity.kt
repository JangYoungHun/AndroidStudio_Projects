package com.example.simpleinput2_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener(){
            try {
                val first = input1.text.toString().toInt()
                val second = input2.text.toString().toInt()
                val sum = first + second
                result.setText("first : $first, second : $second, result : $sum ")
            }
            catch(e: Exception){
                Toast.makeText(applicationContext,"error!!!",Toast.LENGTH_SHORT).show()
            }


        }

    }
}