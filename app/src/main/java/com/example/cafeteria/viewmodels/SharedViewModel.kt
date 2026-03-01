package com.example.cafeteria.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cafeteria.models.Product

class SharedViewModel : ViewModel() {

    private val cartMap = mutableMapOf<Product, Int>()
    private val _cartMapLiveData = MutableLiveData<Map<Product, Int>>(cartMap.toMap())
    val cartMapLiveData: LiveData<Map<Product, Int>> = _cartMapLiveData

    private val _cartTotal = MutableLiveData(0.0)
    val cartTotal: LiveData<Double> = _cartTotal

    fun incrementProduct(product: Product) {
        val current = cartMap[product] ?: 0
        cartMap[product] = current + 1
        _cartMapLiveData.value = cartMap.toMap()
        calculateTotal()
    }

    fun decrementProduct(product: Product) {
        val current = cartMap[product] ?: 0
        if (current > 1) {
            cartMap[product] = current - 1
        } else {
            cartMap.remove(product)
        }
        _cartMapLiveData.value = cartMap.toMap()
        calculateTotal()
    }

    fun removeProduct(product: Product) {
        cartMap.remove(product)
        _cartMapLiveData.value = cartMap.toMap()
        calculateTotal()
    }

    private fun calculateTotal() {
        _cartTotal.value = cartMap.entries.sumOf { it.key.preu * it.value }
    }
}