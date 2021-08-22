package com.example.kotlin_practice

data class Person(var name:String, var age:Int, var mobile:String)

class Book private constructor(var code:String, var title:String, var author:String){

    companion object{
        fun create() = Book("a123", "The Story", "NoName" )
        fun create(code: String, title: String, author: String) = Book(code, title, author)
    }
}


var i =2
fun main(){

  /*  var addOper = {a:Int,b:Int -> a+b}
    val a = 10
    var b = 20
    println(" $a + $b = ${calc(a,b,addOper)} ")

    //뺄셈
    var subResult =calc(a,b){
        a:Int,b:Int -> a-b
    }

    println(" $a - $b = $subResult")
    var criteria ={a:Int -> a > 80}
    println(classify(criteria))
    println(classify({it > 80}))

    var person1 = Person("가나다", 30, "010-1234-5678")
    println(person1)

    var(name1, age1, mobile1) = person1
    println("나는 $name1 입니다. ${age1}살 입니다. 연락처는 $mobile1 입니다. ")

    var book1 = Book.create()
    println("책 정보 코드 : ${book1.code}, 이름 : ${book1.title} 작가 : ${book1.author}")

    var book2 = Book.create("b495", "Fairy Tale", "JYH")
    println("책 정보 코드 : ${book2.code}, 이름 : ${book2.title} 작가 : ${book2.author}".sayHello() )
    */


    var num:Int? = null

    println(num)
    var num2 = num?.let {
        println(it)
        it + 1
    }?:3
    println(num2)

    println(getSquared())
    println(i)

}


fun classify(pass:(Int)->Boolean):Boolean{
        return pass(91)
    }

//fun pass(score:Int):Boolean = score > 80

fun calc(a:Int,b:Int,oper:(Int,Int) -> Int):Int{
    return oper(a,b)
}

fun getSquared() =(i*i).also {
    i++
}




//확장함수
fun String.sayHello() = this + " say Hello~"