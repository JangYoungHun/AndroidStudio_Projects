package com.example.drawerlayout_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

       /* val toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer, R.string.close_drawer )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState() */


        toolbar.setNavigationOnClickListener {
            Toast.makeText(applicationContext,"메뉴 열기 클릭",Toast.LENGTH_SHORT).show()
            drawerLayout.openDrawer(GravityCompat.START);
        }

        navigationView.setNavigationItemSelectedListener {
            Toast.makeText(applicationContext,"클릭",Toast.LENGTH_SHORT).show()
            when(it.itemId){
                R.id.menu1 -> changeFragment(1)
                R.id.menu2 -> changeFragment(2)
                R.id.menu3 -> changeFragment(3)
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }





    }

    private fun changeFragment(i: Int) {
        var fragment:Fragment = Fragment1()

        when(i){
            1 -> fragment = Fragment1()
            2 -> fragment = Fragment2()
            3 -> fragment = Fragment3()
            }
        with(supportFragmentManager.beginTransaction()){
            replace(R.id.container,fragment)
        }.commit()
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else {
            super.onBackPressed()
        }
    }
}