package com.example.create_array_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button1.setOnClickListener{
            textView.text = ""
            val array1 = arrayOf("가나다", "라마바","사아자")
            textView.append(" arrayOf : ${array1.contentToString()} \n")

            val array2 = array1.plus("차카타")
            textView.append(" array.plus : ${array2.contentToString()} \n")

            val array3 = arrayOfNulls<String>(4)
            textView.append(" arrayOfNulls : ${array3.contentToString()} \n")

            array3.set(0,"가나다")
            array3[3] = "차카타"
            textView.append(" arrayOfNulls set, array[] : ${array3.contentToString()} \n")

            val array4 = intArrayOf(1,2,3,4)
            textView.append(" intArrayOf : ${array4.contentToString()} \n")

            val array5 = Array<Int>(5){index -> index*index}
            textView.append(" Array<Int> 람다식 제곱 : ${array5.contentToString()} \n")

            val array6 = emptyArray<Int>()
            textView.append(" emptyArray : ${array6.contentToString()} \n")

            val array7 = array1.sliceArray(0..2)
            textView.append(" slice : ${array7.contentToString()} \n")
        }


        button2.setOnClickListener{
            textView.text = ""

            var list1 = listOf<String>("가나다", "라마바","사아자")
            textView.append(" listOf : ${list1.joinToString()} \n")
            var list2 = emptyList<String>()
            textView.append(" emptyList : ${list2.joinToString()} \n")
            var list3 = mutableListOf<String>("가나다", "라마바","사아자")
            textView.append(" mutableListoF : ${list3.joinToString()} \n")
            list3.add("차카타")
            textView.append(" mutableListoF add item : ${list3.joinToString()} \n")

            var list4 = list1.slice(0..1)
            textView.append(" slice : ${list4.joinToString()} \n")

            var list5 = arrayListOf<Int>()
            list5.add(1)
            list5.add(2)
            list5.add(3)
            textView.append(" arrayListOf add Items : ${list5.joinToString()} \n")

        }

    }
}