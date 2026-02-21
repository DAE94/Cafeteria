package com.example.cafeteria.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeteria.R
import com.example.cafeteria.models.Product
import com.example.cafeteria.viewmodels.SharedViewModel

class CartAdapter(
    private val sharedViewModel: SharedViewModel
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cartItems: List<Product> = emptyList()

    fun submitList(items: List<Product>) {
        cartItems = items
        notifyDataSetChanged()
    }

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.tvName)
        val priceText: TextView = view.findViewById(R.id.tvPrice)
        val removeButton: Button = view.findViewById(R.id.btnRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cartItems[position]
        holder.nameText.text = product.name
        holder.priceText.text = "€${product.price}"
        holder.removeButton.setOnClickListener {
            sharedViewModel.removeFromCart(product)
        }
    }

    override fun getItemCount(): Int = cartItems.size
}