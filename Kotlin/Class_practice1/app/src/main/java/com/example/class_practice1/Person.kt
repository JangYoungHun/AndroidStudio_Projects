package com.example.class_practice1

import android.content.Context
import android.widget.Toast

open class Person(var name:String?) {

   // var name:String? = null
    var age:Int = 0
    var address:String? = null

    init {
        println("Person 기본생성자 호출")
    }

    constructor(name: String, age:Int, address:String):this(name){
        println("Person 두 번째 생성자 호출")
        this.name = name
        this.age=age
        this.address = address
    }

    open fun whoAmI(){
        println("안녕 나는 Person인 ${this.name} 이야 나이는 ${this.age} 주소는 ${this.address} ")
    }



}