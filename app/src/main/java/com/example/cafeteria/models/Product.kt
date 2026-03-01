package com.example.cafeteria.models

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

data class Product(
    val id: String = "",
    val nom: String = "",
    val categoria: String = "",
    val preu: Double = 0.0,
    val stock: Long = 0,
    val imatge: String = "",
    val descripcio: String = ""
)

fun getProductsByCategory(category: String, onResult: (List<Product>) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("productes")
        .whereEqualTo("categoria", category)
        .get()
        .addOnSuccessListener { snapshot ->
            val products = snapshot.documents.map { doc ->
                Product(
                    id = doc.id,
                    nom = doc.getString("nom") ?: "",
                    categoria = doc.getString("categoria") ?: "",
                    preu = doc.getDouble("preu") ?: 0.0,
                    stock = doc.getLong("stock") ?: 0,
                    imatge = doc.getString("imatge") ?: "",
                    descripcio = doc.getString("descripcio") ?: ""
                )
            }
            onResult(products)
        }
        .addOnFailureListener { e ->
            Log.e("Firestore", "Error loading products", e)
            onResult(emptyList())
        }
}