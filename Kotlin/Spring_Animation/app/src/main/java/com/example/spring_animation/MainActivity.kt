package com.example.spring_animation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintSet
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val springArm = imageView.let {
            SpringAnimation(it,DynamicAnimation.TRANSLATION_Y,0f)
        }
        springArm.spring.dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        springArm.spring.stiffness = SpringForce.STIFFNESS_VERY_LOW



        imageView.setOnTouchListener { v, event ->

            val startY = v.y

            when(event.action){
                MotionEvent.ACTION_MOVE -> {
                    imageView.y += event.y
                }

                MotionEvent.ACTION_UP -> {

                    springArm.start()

                }
            }


            true
        }


    }
}