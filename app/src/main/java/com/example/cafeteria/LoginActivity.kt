package com.example.cafeteria

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class LoginActivity : AppCompatActivity() {

    // 🔹 Usuarios provisionales (email -> contraseña)
    private val users = mapOf(
        "usuario1@example.com" to "1234",
        "david@gmail.com" to "abcd",
        "test@test.com" to "pass123",
        "a" to "a"
    )


        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Botó Login
        findViewById<Button>(R.id.btnLogin).setOnClickListener {

            val email = findViewById<EditText>(R.id.etEmail).text.toString().trim()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()

            // Validaciones básicas
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Introduce correo y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            // Validación de formato
//            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                Toast.makeText(this, "Correo no válido", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }

            // 1) Existe el usuario
            if (!users.containsKey(email)) {
                Toast.makeText(this, "Usuario no existente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2) Contraseña correcta
            val storedPassword = users[email]
            if (storedPassword != password) {
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 3) Login OK
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        // Text Registrar (TextView)
        findViewById<TextView>(R.id.tvRegister).setOnClickListener {
            startActivity(Intent(this, RegistreActivity::class.java))
        }
    }

}