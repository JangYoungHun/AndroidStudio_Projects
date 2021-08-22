package com.example.imageview_touchevent_practice

import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.core.view.marginTop
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var startX =0.0f
    var startY =0.0f

    var leftMargin = 0
    var topMargin =0

    var image_width = 0.0f
    var image_height = 0.0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        image_width = resources.getDimension(R.dimen.image_width)
        image_height = resources.getDimension(R.dimen.image_height)

        image1.setOnTouchListener { v, event ->

            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                    startY = event.y
                }
                MotionEvent.ACTION_MOVE -> {

                    leftMargin += (event.x - startX).toInt()
                    topMargin += (event.y - startY).toInt()
                    val layoutParams = FrameLayout.LayoutParams(
                        image_width.toInt(),
                        image_height.toInt()
                    )


                    layoutParams.leftMargin = leftMargin
                    layoutParams.topMargin = topMargin
                    image1.layoutParams = layoutParams
                }

                MotionEvent.ACTION_UP -> {

                }
            }

            true
        }

    }
}