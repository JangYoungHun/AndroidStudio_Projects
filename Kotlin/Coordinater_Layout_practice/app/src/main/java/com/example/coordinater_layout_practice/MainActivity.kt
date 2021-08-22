package com.example.coordinater_layout_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            showToast("Navigation Icon 클릭")
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_custom,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        showToast("클릭")
        when(item.itemId){

            R.id.tab1 -> showToast("tab1 클릭")
            R.id.tab2 -> showToast("tab2 클릭")
            R.id.tab3 -> showToast("tab3 클릭")
        }

        return super.onOptionsItemSelected(item)
    }


    fun showToast(message:String){
        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
    }
}