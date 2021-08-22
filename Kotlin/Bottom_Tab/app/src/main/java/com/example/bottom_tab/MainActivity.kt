package com.example.bottom_tab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(supportFragmentManager.beginTransaction()){
            val fragment1 = Fragment1()
            replace(R.id.container,fragment1)
        }.commit()



        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.tab1  -> {with(supportFragmentManager.beginTransaction()){
                   var fragment1 = Fragment1()
                    replace(R.id.container,fragment1)
                }.commit()}
                R.id.tab2-> {with(supportFragmentManager.beginTransaction()){
                    var fragment2 = Fragment2()
                    replace(R.id.container,fragment2)
                }.commit()}
                R.id.tab3-> {with(supportFragmentManager.beginTransaction()){
                    var fragment3 = Fragment3()
                    replace(R.id.container,fragment3)
                }.commit()}
            }

            true
        }

    }

}