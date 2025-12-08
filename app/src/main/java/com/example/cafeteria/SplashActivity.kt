package com.example.cafeteria

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Thread con sleep (como pediste). No es lo ideal en producción, pero funciona.
        Thread {
            try { Thread.sleep(2000) } catch (e: InterruptedException) { /* ignored */ }
            runOnUiThread {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }.start()
    }
}