package com.example.daggerhilt_practice

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var testString1: String

    @Inject
    lateinit var sandwich: Sandwich

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // println("Test String1 : $testString1")

        //println(sandwich)
        Log.d("MainActivity", "빵 이름 : ${sandwich.bread.name}  빵 가격 : ${sandwich.bread.price}")
        Log.d("MainActivity", "치즈 이름 : ${sandwich.cheeze.name}  치즈 가격 : ${sandwich.cheeze.price}")
        Log.d("MainActivity", "채소 이름 : ${sandwich.vegatable.name}  채소 가격 : ${sandwich.vegatable.price}")
    }
}