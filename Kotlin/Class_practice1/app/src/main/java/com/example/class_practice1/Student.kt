package com.example.class_practice1

class Student(name:String) : Person(name) {

    init {
        println("Student 기본생성자 호출")
    }

    constructor(name:String, age:Int, address:String):this(name){
        println("Student 두 번째 생성자 호출")

        this.name = name
        this.age = age
        this.address = address
    }

    override fun whoAmI() {
        println("안녕 나는 Student인 ${this.name} 이야 나이는 ${this.age} 주소는 ${this.address} ")
        //super.whoAmI()
    }

    }
