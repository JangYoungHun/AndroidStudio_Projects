package com.example.function_practice2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button1.setOnClickListener(){
            var oper = operator("add")
            textView1.setText(doCalc{
                a:Int,b:Int->a+b
            }.toString())
        }

        button2.setOnClickListener(){
            var oper = operator("sub")
            textView2.setText(doCalc(oper).toString())
        }


        button3.setOnClickListener(){
            var oper = operator("mul")
            textView3.setText(doCalc(oper).toString())
        }


        button4.setOnClickListener(){
            var oper = operator("div")
            textView4.setText(doCalc(oper).toString())
        }

    }

    fun operator(oper:String):((Int,Int)->Int)? {

        var function:((Int,Int) -> Int)? = null

        when(oper){
            "add","Add" -> function = {a:Int,b:Int -> a+b}
            "sub" -> function = {a:Int,b:Int -> a-b}
            "mul" -> function = {a:Int,b:Int -> a*b}
            "div" -> function = {a:Int,b:Int -> a/b}
            else -> {}
        }

        return function
    }



    fun doCalc(oper:((Int,Int) -> Int)?):Int{
        if(oper != null){
            try {
                var num1:Int =  input1.text.toString().toInt()
                var num2:Int =  input2.text.toString().toInt()
                var result = doAction(num1, num2, oper!!)
                return result
            }
                catch (e:Exception){
                    showToast("에러발생 숫자만 입력")
                }
        }
        else{
            showToast("연산자 null 에러 발생")
        }
        return -1
    }


    fun doAction(num1:Int, num2:Int, oper:(Int,Int)->Int) : Int{
        try {
            return oper(num1,num2)
        }
        catch (e:Exception){
            e.printStackTrace();
            showToast("에러발생 숫자 확인")
            return -1
        }
    }


    fun showToast(message:String){
        Toast.makeText(applicationContext,message, Toast.LENGTH_SHORT).show()
    }
}