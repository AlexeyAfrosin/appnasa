package com.afrosin.appnasa.view

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.afrosin.appnasa.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.ivSplashScreen.animate().rotationBy(750f)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(3000)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {}

                override fun onAnimationEnd(p0: Animator?) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }

                override fun onAnimationCancel(p0: Animator?) {}

                override fun onAnimationRepeat(p0: Animator?) {}
            })


    }


}

