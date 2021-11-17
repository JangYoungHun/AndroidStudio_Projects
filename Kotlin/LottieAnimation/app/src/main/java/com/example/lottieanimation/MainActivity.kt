package com.example.lottieanimation

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lottieanimation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivity.root)

        var state = false

        mainActivity.animationView.setOnClickListener {

            var animator = when (state) {
                false ->  ValueAnimator.ofFloat(0f, 1f).setDuration(1000)
                true ->  ValueAnimator.ofFloat(1f, 0f).setDuration(1000)
            }

            state = !state
            animator.addUpdateListener {
                mainActivity.animationView.progress = it.animatedValue as Float
            }
            animator.start()

        }

    }
}