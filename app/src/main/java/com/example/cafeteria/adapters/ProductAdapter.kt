package com.example.cafeteria.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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
        val productId = products[position].id
        val product = sharedViewModel.getLatestProductVersion(productId) ?: products[position]

        holder.nameText.text = product.nom
        holder.priceText.text = "€%.2f".format(product.preu)

        // ===============================
        // Quantitat actual des del SharedViewModel
        // ===============================
        val qty = sharedViewModel.getQuantity(product)
        holder.quantityText.text = qty.toString()

        Log.d("ProductAdapter", "Bind product: ${product.nom}, qty=$qty, pos=$position")

        // Habilitar/deshabilitar botons segons stock
        holder.plusBtn.isEnabled = qty < product.stock
        holder.plusBtn.alpha = if (holder.plusBtn.isEnabled) 1f else 0.5f
        holder.minusBtn.isEnabled = qty > 0
        holder.minusBtn.alpha = if (holder.minusBtn.isEnabled) 1f else 0.5f

        // ===============================
        // Incrementar cantidad
        // ===============================
        holder.plusBtn.setOnClickListener {
            val currentQty = sharedViewModel.getQuantity(product)
            Log.d("ProductAdapter", "PLUS clicked for: ${product.nom}, currentQty=$currentQty, stock=${product.stock}")
            if (currentQty >= product.stock-1) {
                Log.d("ProductAdapter", "Cantidad máxima alcanzada, mostrando Toast")
                Toast.makeText(
                    holder.itemView.context,
                    "Quantitat màxima de ${product.nom}",
                    Toast.LENGTH_SHORT
                ).show()
                sharedViewModel.addOne(product)
            } else if (currentQty > product.stock) {
                //no hauria d'entrar mai
                Log.d("ProductAdapter","està afegint més quantitat del stock. Botó no deshabilitat?.")
                Toast.makeText(
                    holder.itemView.context,
                    "No hi ha prou ${product.nom}, aquest botó hauria d'estar deshabilitat",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                sharedViewModel.addOne(product)
                notifyItemChanged(position) // actualitzar només aquesta fila
            }
        }

        // ===============================
        // Decrementar quantitat
        // ===============================
        holder.minusBtn.setOnClickListener {
            val currentQty = sharedViewModel.getQuantity(product)
            if (currentQty > 0) {
                sharedViewModel.removeOne(product)
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount() = products.size

    fun replaceData(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}