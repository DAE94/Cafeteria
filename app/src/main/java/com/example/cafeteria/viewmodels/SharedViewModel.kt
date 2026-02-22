package com.example.cafeteria.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cafeteria.models.Product

class SharedViewModel : ViewModel() {

    // 🔹 cantidades reales por producto
    private val quantities = mutableMapOf<Product, Int>()

    private val _cartItems = MutableLiveData<Map<Product, Int>>(emptyMap())
    val cartItems: LiveData<Map<Product, Int>> = _cartItems

    private val _cartTotal = MutableLiveData(0.0)
    val cartTotal: LiveData<Double> = _cartTotal


    // ➕ sumar 1 unidad
    fun addOne(product: Product) {
        val current = quantities[product] ?: 0
        quantities[product] = current + 1
        publish()
    }

    // ➖ restar 1 unidad
    fun removeOne(product: Product) {
        val current = quantities[product] ?: 0

        if (current > 1) {
            quantities[product] = current - 1
        } else {
            quantities.remove(product)
        }

        publish()
    }

    fun getQuantity(product: Product): Int {
        return quantities[product] ?: 0
    }

    private fun publish() {
        _cartItems.value = quantities.toMap()
        _cartTotal.value =
            quantities.entries.sumOf { it.key.price * it.value }
    }
}