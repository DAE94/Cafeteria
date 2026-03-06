package com.example.cafeteria.adapters

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
        val product = products[position]

        holder.nameText.text = product.nom
        holder.priceText.text = "€%.2f".format(product.preu)

        // ===============================
        // Cantidad actual desde SharedViewModel
        // ===============================
        val qty = sharedViewModel.getQuantity(product)
        holder.quantityText.text = qty.toString()

        // Habilitar/deshabilitar botones según stock
        holder.plusBtn.isEnabled = qty < product.stock
        holder.minusBtn.isEnabled = qty > 0

        // ===============================
        // Incrementar cantidad
        // ===============================
        holder.plusBtn.setOnClickListener {
            val currentQty = sharedViewModel.getQuantity(product)
            if (currentQty >= product.stock) {
                Toast.makeText(
                    holder.itemView.context,
                    "Quantitat màxima. No hi ha més ${product.nom}",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                sharedViewModel.addOne(product)
                notifyItemChanged(position) // actualizar solo esta fila
            }
        }

        // ===============================
        // Decrementar cantidad
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