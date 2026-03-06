package com.example.cafeteria.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cafeteria.models.CartItem
import com.example.cafeteria.models.Product


class SharedViewModel : ViewModel() {

    private val cartMap = mutableMapOf<String, CartItem>()

    private val _cartMapLiveData = MutableLiveData<Map<String, CartItem>>(cartMap.toMap())
    val cartMapLiveData: LiveData<Map<String, CartItem>> = _cartMapLiveData

    private val _cartTotal = MutableLiveData(0.0)
    val cartTotal: LiveData<Double> = _cartTotal

    // ----------------------
    // FUNCIONS
    // ----------------------

    fun addOne(product: Product) {
        val existing = cartMap[product.id]
        if (existing != null) {
            existing.quantity++
        } else {
            cartMap[product.id] = CartItem(product, 1)
        }
        notifyChanges()
    }

    fun removeOne(product: Product) {
        val existing = cartMap[product.id] ?: return
        if (existing.quantity > 1) {
            existing.quantity--
        } else {
            cartMap.remove(product.id)
        }
        notifyChanges()
    }

    fun removeProduct(product: Product) {
        cartMap.remove(product.id)
        notifyChanges()
    }

    fun getCartItems(): List<CartItem> {
        return cartMap.values.toList()
    }

    fun getCartTotal(): Double {
        return cartMap.values.sumOf { it.product.preu * it.quantity }
    }

    fun getQuantity(product: Product): Int {
        return cartMap[product.id]?.quantity ?: 0
    }

    fun getCartMap(): Map<String, CartItem> = cartMap.toMap()

    fun clearCart() {
        cartMap.clear()
        notifyChanges()
    }

    // ----------------------
    // Helpers
    // ----------------------

    private fun notifyChanges() {
        // Para LiveData del fragment
        _cartMapLiveData.value = cartMap.toMap()
        _cartTotal.value = getCartTotal()
    }
}