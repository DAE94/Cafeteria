package com.example.cafeteria.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeteria.R
import com.example.cafeteria.models.Product
import com.example.cafeteria.viewmodels.SharedViewModel

class CartAdapter(
    private val sharedViewModel: SharedViewModel
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cartMap = mapOf<Product, Int>()

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
        val product = cartMap.keys.toList()[position]
        val quantity = cartMap[product] ?: 1

        holder.nameText.text = product.name
        holder.quantityText.text = quantity.toString()
        holder.priceText.text = "€%.2f".format(product.price * quantity)

        holder.minusBtn.isEnabled = quantity > 1

        holder.plusBtn.setOnClickListener {
            sharedViewModel.incrementProduct(product)
        }

        holder.minusBtn.setOnClickListener {
            sharedViewModel.decrementProduct(product)
        }

        holder.removeBtn.setOnClickListener {
            sharedViewModel.removeProduct(product)
        }
    }

    override fun getItemCount(): Int = cartMap.size

    // actualizar lista desde el fragment
    fun submitMap(map: Map<Product, Int>) {
        cartMap = map
        notifyDataSetChanged()
    }
}