package com.example.cafeteria

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import android.animation.Animator
import android.animation.AnimatorListenerAdapter

class SplashActivity : AppCompatActivity() {

    // Temps mínim d'animació
    private val MIN_SPLASH_DURATION = 3000L // 3 segons

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val animationView = findViewById<LottieAnimationView>(R.id.lottieSplash)

        // Desactivar loop
        animationView.repeatCount = 0

        val startTime = System.currentTimeMillis()

        // Listener per quan acabi l'animació
        animationView.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                val elapsed = System.currentTimeMillis() - startTime
                val delay = if (elapsed < MIN_SPLASH_DURATION) {
                    MIN_SPLASH_DURATION - elapsed
                } else 0L

                // Passar a LoginActivity dresprés del delay
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    finish()
                }, delay)
            }
        })
    }
}