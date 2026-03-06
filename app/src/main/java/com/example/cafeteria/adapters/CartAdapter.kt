package com.example.cafeteria.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeteria.R
import com.example.cafeteria.models.CartItem
import com.example.cafeteria.viewmodels.SharedViewModel

class CartAdapter(
    private val sharedViewModel: SharedViewModel
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cartItems = listOf<CartItem>()

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.tvName)
        val priceText: TextView = view.findViewById(R.id.tvPrice)
        val minusBtn: Button = view.findViewById(R.id.btnMinus)
        val plusBtn: Button = view.findViewById(R.id.btnPlus)
        val quantityText: TextView = view.findViewById(R.id.tvQuantity)
        val removeBtn: Button = view.findViewById(R.id.btnRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        val item = cartItems[position]
        val product = item.product
        val quantity = item.quantity

        holder.nameText.text = product.nom
        holder.quantityText.text = quantity.toString()
        holder.priceText.text = "%.2f€".format(product.preu * quantity)

        holder.minusBtn.isEnabled = quantity > 1
        holder.plusBtn.isEnabled = quantity < product.stock

        holder.plusBtn.setOnClickListener {
            if (quantity < product.stock) {
                sharedViewModel.addOne(product)
            } else {
                android.widget.Toast.makeText(
                    holder.itemView.context,
                    "Quantitat màxima. No hi ha més ${product.nom}",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
        }

        holder.minusBtn.setOnClickListener {
            sharedViewModel.removeOne(product)
        }

        holder.removeBtn.setOnClickListener {
            sharedViewModel.removeProduct(product)
        }
    }

    override fun getItemCount(): Int = cartItems.size

    fun submitMap(map: Map<String, CartItem>) {
        cartItems = map.values.toList()
        notifyDataSetChanged()
    }
}