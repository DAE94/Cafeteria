package com.example.cafeteria.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cafeteria.models.Product

class SharedViewModel : ViewModel() {

    private val _cartItems = MutableLiveData<MutableList<Product>>(mutableListOf())
    val cartItems: LiveData<MutableList<Product>> = _cartItems

    private val _cartTotal = MutableLiveData(0.0)
    val cartTotal: LiveData<Double> = _cartTotal

    fun addToCart(product: Product) {
        val list = _cartItems.value ?: mutableListOf()
        list.add(product)
        _cartItems.value = list
        calculateTotal()
    }

    fun removeFromCart(product: Product) {
        val list = _cartItems.value ?: mutableListOf()
        list.remove(product)
        _cartItems.value = list
        calculateTotal()
    }

    private fun calculateTotal() {
        _cartTotal.value = _cartItems.value?.sumOf { it.price } ?: 0.0
    }
}