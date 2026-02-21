package com.example.cafeteria

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Botó Login
        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            // Obrir HomeActivity
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        // Text Registrar (TextView)
        findViewById<TextView>(R.id.tvRegister).setOnClickListener {
            startActivity(Intent(this, RegistreActivity::class.java))
        }
    }

}