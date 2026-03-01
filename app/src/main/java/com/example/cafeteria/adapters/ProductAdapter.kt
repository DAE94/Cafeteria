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

class ProductAdapter(
    private var products: List<Product>,
    private val sharedViewModel: SharedViewModel
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.tvName)
        val priceText: TextView = view.findViewById(R.id.tvPrice)
        val increaseButton: Button = view.findViewById(R.id.btnPlus)
        val decreaseButton: Button = view.findViewById(R.id.btnMinus)
        val removeButton: Button = view.findViewById(R.id.btnRemove)
    }

    fun replaceData(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        holder.nameText.text = product.nom
        holder.priceText.text = "€${product.preu}"

        val quantity = sharedViewModel.getQuantity(product)
        holder.quantityText.text = quantity.toString()

        holder.increaseButton.setOnClickListener {
            sharedViewModel.incrementProduct(product)
        }

        holder.decreaseButton.setOnClickListener {
            sharedViewModel.decrementProduct(product)
        }
    }

    override fun getItemCount() = products.size
}
