package com.example.daggerhilt_practice

import javax.inject.Inject

class Sandwich @Inject constructor(var bread:Bread, var cheeze:Cheeze, var vegatable:Vegatable) {
}

data class Bread constructor(val name:String, var price:Int)
data class Cheeze constructor(val name:String, var price:Int)
data class Vegatable constructor(val name:String, var price:Int)
