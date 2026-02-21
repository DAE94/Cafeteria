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

import java.util.List;

class ProductAdapter(
    private val products:kotlin.collections.List<Product>,
    private val sharedViewModel: SharedViewModel
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nameText: TextView = view.findViewById(R.id.tvName)
    val priceText: TextView = view.findViewById(R.id.tvPrice)
    val addButton: Button = view.findViewById(R.id.btnAdd)
}

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
    val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
    return ProductViewHolder(view)
}

override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
    val product = products[position]
    holder.nameText.text = product.name
    holder.priceText.text = "€${product.price}"
    holder.addButton.setOnClickListener {
        sharedViewModel.addToCart(product)
    }
}

override fun getItemCount() = products.size
}
