package com.example.class_practice1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    enum class PERSON_TYPE{
        PERSON, STUDENT
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1.setOnClickListener{
            val person1 = Person("David", 20, "seoul")
            person1.whoAmI()
        }
      button2.setOnClickListener{
          val student1 = Student("David", 20, "seoul")
          student1.whoAmI()
      }
        button2.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {

            }
        })

        button2.setOnClickListener({v->})

        button2.setOnClickListener(){
            v->println()
        }

    }
}