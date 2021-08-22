package com.example.viewpager_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import androidx.viewpager2.adapter.FragmentStateAdapter as FragmentStateAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout.addTab(tabLayout.newTab().setText("Tab1"))
        tabLayout.addTab(tabLayout.newTab().setText("Tab2"))
        tabLayout.addTab(tabLayout.newTab().setText("Tab3"))



        viewPager.adapter = MyAdapter(supportFragmentManager,lifecycle)
        viewPager.orientation =  ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.offscreenPageLimit =3


        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                showToast("$position 번 Page선택 ")
            }
        })

        TabLayoutMediator(tabLayout, viewPager){ tab,position ->
            tab.text = "${position+1}번 Tab"

        }.attach()
    }

    fun showToast(message:String){
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }


    inner class MyAdapter : FragmentStateAdapter {

        constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle) : super(
            fragmentManager,
            lifecycle
        )

        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
          return when(position){
              0 -> {Fragment1()}
              1 -> {Fragment2()}
              2 -> {Fragment3()}
              else -> {Fragment1()}
          }
        }
    }

}