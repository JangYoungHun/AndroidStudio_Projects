package com.example.codeblock_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

data class Person(
    var name:String? = null,
    var age:Int? = null,
    var mobile:String? = null
    )


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button.setOnClickListener{
            val person1 = Person()
            person1?.name = "홍길동"
            person1?.age = 20
            person1.mobile = "010-1234-5678"

            //중복제거

            val person2:Person? = Person().apply {
                name = "홍길동"
                age = 20
                mobile = "010-1234-5678"
            }


            showToast("이름 : ${person2?.name}")
            showToast("나이 : ${person2?.age}")
            showToast("연락처 : ${person2?.mobile}")

            //중복제거
            with(person2!!){
                showToast("이름 : $name")
                showToast("나이 : $age")
                showToast("연락처 : $mobile")
            }

            if(person2 != null) {
                // if문 으로 null 확인해서 ? 없이 가능
                showToast("이름 : ${person2.name}")
                showToast("나이 : ${person2.age}")
                showToast("연락처 : ${person2.mobile}")
            }
            else{}

            //if ->
            var result = person2?.let{
                showToast("이름 : $it.name")
                showToast("나이 : $it.age")
                showToast("연락처 : $it.mobile")
                20
            } ?: run {
                //else -> rund
            }

            println("결과 $result ")

            person2?.run {
                showToast("이름 : $name")
                showToast("나이 : $age")
                showToast("연락처 : $mobile")
            }?: run {
                showToast("null 입니다")
            }

            val person3 = Person()
            if(person3.name == null){
                showToast("null 객체 입니다.")
            }
            if(person3.age == null){
                showToast("null 객체 입니다.")
            }


            //중복제거
            val person4 = Person()
            val person5 = person4.also {
                if(it.name == null){
                    showToast("null 객체 입니다.")
                }
                it.name = "홍길동4"
                if(it.age == null){
                    showToast("null 객체 입니다.")
                }
                it.age = 21
            }



        }

    }

    fun showToast(message:String){
        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
    }

}