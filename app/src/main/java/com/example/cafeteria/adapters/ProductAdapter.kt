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
    private val products: kotlin.collections.List<Product>,
    private val sharedViewModel: SharedViewModel
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.tvName)
        val priceText: TextView = view.findViewById(R.id.tvPrice)

        val minusBtn: Button = view.findViewById(R.id.btnMinus)
        val plusBtn: Button = view.findViewById(R.id.btnPlus)
        val quantityText: TextView = view.findViewById(R.id.tvQuantity)
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

        // 🔹 leer SIEMPRE del ViewModel (NO variable local)
        val qty = sharedViewModel.getQuantity(product)

        holder.quantityText.text = qty.toString()
        holder.minusBtn.isEnabled = qty > 0

        holder.plusBtn.setOnClickListener {
            sharedViewModel.addOne(product)
            notifyItemChanged(position)
        }

        holder.minusBtn.setOnClickListener {
            sharedViewModel.removeOne(product)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount() = products.size
}
