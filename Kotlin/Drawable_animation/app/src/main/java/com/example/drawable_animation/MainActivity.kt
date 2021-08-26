package com.example.drawable_animation

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var anim1:AnimationDrawable
    lateinit var anim2:Animator
    lateinit var animSet:AnimatorSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        anim1 = imageView.background as AnimationDrawable

        imageView.setOnClickListener {
            anim1.start()
        }

        anim2 = AnimatorInflater.loadAnimator(applicationContext, R.animator.anim2)
        anim2.setTarget(imageView2)

        imageView2.setOnClickListener {
            anim2.start()
        }

        val anim3 = ObjectAnimator.ofFloat(imageView3,"rotationX",360f)
        anim3.duration = 3000;
        val anim4 = ObjectAnimator.ofFloat(imageView3,"translationX",800f)
        anim4.duration = 3000;
        val anim5 = ObjectAnimator.ofFloat(imageView3,"rotationY",360f)
        anim5.duration = 3000;

        animSet = AnimatorSet().apply {
            play(anim3).with(anim4)
            play(anim4).with(anim5)
        }

        imageView3.setOnClickListener {
            animSet.interpolator = AccelerateDecelerateInterpolator()
   //         animSet.start()
            animSet.reverse()
        }

        val animatedVector:AnimatedVectorDrawable = ContextCompat.getDrawable(applicationContext,R.drawable.animated_drawable) as AnimatedVectorDrawable
        imageView4.setImageDrawable(animatedVector);
        imageView4.setOnClickListener {
            animatedVector.start()
        }
    }
}