package com.example.cafeteria.fragments

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.cafeteria.R
import com.example.cafeteria.viewmodels.SharedViewModel
import com.google.firebase.firestore.FirebaseFirestore
import androidx.navigation.fragment.findNavController



class PagamentFragment : Fragment(R.layout.fragment_pagament) {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val db = FirebaseFirestore.getInstance()

    private lateinit var etCard: EditText
    private lateinit var etOwner: EditText
    private lateinit var etExpiry: EditText
    private lateinit var etCvv: EditText
    private lateinit var btnPay: Button
    private lateinit var tvCancelPayment: TextView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etCard = view.findViewById(R.id.etCard)
        etOwner = view.findViewById(R.id.etCardOwner)
        etExpiry = view.findViewById(R.id.etExpiry)
        etCvv = view.findViewById(R.id.etCvv)
        btnPay = view.findViewById(R.id.btnConfirmPay)
        tvCancelPayment = view.findViewById(R.id.tvCancelPayment)


        //Botó pagar
        btnPay.setOnClickListener {
            if (validateForm()) {
                processPayment()
            }
        }

        //Text cancel·lar pagament
        tvCancelPayment.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    // =========================
    // VALIDACIONS
    // =========================

    private fun validateForm(): Boolean {

        val card = etCard.text.toString().replace(" ", "")
        val owner = etOwner.text.toString().trim()
        val expiry = etExpiry.text.toString()
        val cvv = etCvv.text.toString()

        // Tarjeta
        if (card.length != 16 || !card.all { it.isDigit() } || !isValidCard(card)) {
            etCard.error = "Targeta no vàlida"
            return false
        }

        // Titular
        if (owner.length < 3) {
            etOwner.error = "Nom massa curt"
            return false
        }

        // Data
        if (!isValidExpiry(expiry)) {
            etExpiry.error = "Data no vàlida"
            return false
        }

        // CVV
        if (cvv.length != 3 || !cvv.all { it.isDigit() }) {
            etCvv.error = "CVV incorrecte"
            return false
        }

        return true
    }

    // =========================
    // Luhn (validació tarjeta)
    // =========================

    private fun isValidCard(number: String): Boolean {
        var sum = 0
        var alternate = false

        for (i in number.length - 1 downTo 0) {
            var n = number[i] - '0'

            if (alternate) {
                n *= 2
                if (n > 9) n -= 9
            }

            sum += n
            alternate = !alternate
        }

        return sum % 10 == 0
    }

    // =========================
    // Data MM/AA vàlida
    // =========================

    private fun isValidExpiry(expiry: String): Boolean {

        val regex = Regex("^(0[1-9]|1[0-2])/([0-9]{2})$")
        if (!regex.matches(expiry)) return false

        val (mm, yy) = expiry.split("/")

        val month = mm.toInt()
        val year = 2000 + yy.toInt()

        val now = Calendar.getInstance()

        val currentYear = now.get(Calendar.YEAR)
        val currentMonth = now.get(Calendar.MONTH) + 1

        return year > currentYear || (year == currentYear && month >= currentMonth)
    }

    // =========================
    // Pagament
    // =========================

    private fun processPayment() {

        btnPay.isEnabled = false
        btnPay.text = "Processant..."

        val cart = sharedViewModel.getCartItems()

        db.runTransaction { transaction ->

            for (item in cart) {

                val ref = db.collection("productes").document(item.product.id)

                val snapshot = transaction.get(ref)

                val currentStock = snapshot.getLong("stock") ?: 0

                if (currentStock < item.quantity) {
                    throw Exception("No hi ha prou stock del producte ${item.product.nom}")
                }

                transaction.update(ref, "stock", currentStock - item.quantity)
            }

        }.addOnSuccessListener {

            sharedViewModel.clearCart()

            Toast.makeText(requireContext(), "Pagament correcte!", Toast.LENGTH_SHORT).show()

            findNavController().popBackStack()

        }.addOnFailureListener { e ->

            btnPay.isEnabled = true
            btnPay.text = "Pagar"

            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }
}