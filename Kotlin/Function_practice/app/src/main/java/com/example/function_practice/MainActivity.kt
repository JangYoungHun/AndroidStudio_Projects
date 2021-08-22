package com.example.function_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1.setOnClickListener(){
            textView1.setText(add1(10,5).toString())
        }
        button2.setOnClickListener(){
            textView2.setText(add2(10,5).toString())
        }
        button3.setOnClickListener(){
            textView3.setText(add3(10,5).toString())
        }
        button4.setOnClickListener(){
            textView4.setText(sum(1,2,3,4,5).toString())
        }
        button5.setOnClickListener(){
            var addRamda = {a:Int, b:Int -> a+b}
            textView5.setText(add4(5,6,addRamda).toString())
        }
    }

    fun add4(a:Int, b:Int, oper:(Int,Int)->Int):Int{
        return oper(a,b)
    }


    fun sum(vararg nums:Int):Int{
        var total:Int = 0
        for (num in nums){
            total+=num
        }
        return total
    }

    fun add1(a:Int,b:Int):Int{
        return a+b
    }

    fun add2(a:Int, b:Int):Int = a+b

    fun add3(a:Int, b:Int) = a+b

    fun showToast(message:String){
        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
    }

}