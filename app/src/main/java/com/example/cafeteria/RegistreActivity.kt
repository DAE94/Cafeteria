package com.example.cafeteria

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.e
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistreActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registre)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val etNom = findViewById<EditText>(R.id.etNom)
        val etCognom = findViewById<EditText>(R.id.etCognom)
        val etTelefon = findViewById<EditText>(R.id.etTelefon)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {

            val nom = etNom.text.toString().trim()
            val cognom = etCognom.text.toString().trim()
            val telefon = etTelefon.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            //Validacionss bàsiques
            if (nom.isEmpty() || cognom.isEmpty() || telefon.isEmpty()
                || email.isEmpty() || password.isEmpty()
            ) {
                Toast.makeText(this, "Omple tots els camps", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(
                    this,
                    "La contrassenya ha de tenir mínim 6 caràcters",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //Crear usuari a Firebase Auth
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->

                    //Guardar la UID no nula a una variable
                    val uid = auth.currentUser!!.uid

                    //Mapejar info usuari
                    val userData = mapOf(
                        "uid" to uid,
                        "nom" to nom,
                        "cognom" to cognom,
                        "telefon" to telefon,
                        "email" to email,
                        "dataCreat" to System.currentTimeMillis()
                    )
                    //Guardar usuari a Firestore
                    db.collection("usuaris")
                        .document(uid)
                        .set(userData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Registre finalitzat!", Toast.LENGTH_SHORT).show()

                            //Tornar al login
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }
                        //Falla guardar l'usuari a Firestore
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this,
                                "Error guardant dades ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()

                        }

                }
                //Falla guardar usuari a Firebase Auth
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        "L'usuari ja existeix o l'email no és vàlid",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}
