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
        val qty = item.quantity

        holder.nameText.text = product.nom
        holder.quantityText.text = qty.toString()
        holder.priceText.text = "%.2f€".format(product.preu * qty)


        // Habilitar/deshabilitar botons segons stock
        holder.plusBtn.isEnabled = qty < product.stock
        holder.plusBtn.alpha = if (holder.plusBtn.isEnabled) 1f else 0.5f
        holder.minusBtn.isEnabled = qty > 1
        holder.minusBtn.alpha = if (holder.minusBtn.isEnabled) 1f else 0.5f


        // ===============================
        // Incrementar cantidad
        // ===============================
        holder.plusBtn.setOnClickListener {
            if (qty >= product.stock-1) {
                Log.d("ProductAdapter", "Cantidad máxima alcanzada, mostrando Toast")
                Toast.makeText(
                    holder.itemView.context,
                    "Quantitat màxima de ${product.nom}",
                    Toast.LENGTH_SHORT
                ).show()
                sharedViewModel.addOne(product)
            } else if (qty > product.stock) {
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

//        holder.plusBtn.setOnClickListener {
//            if (qty < product.stock) {
//                sharedViewModel.addOne(product)
//            } else {
//                android.widget.Toast.makeText(
//                    holder.itemView.context,
//                    "Quantitat màxima. No hi ha més ${product.nom}",
//                    android.widget.Toast.LENGTH_SHORT
//                ).show()
//            }
//        }

        holder.minusBtn.setOnClickListener {
            if (qty > 1) {
                sharedViewModel.removeOne(product)
            } else {
                Toast.makeText(
                    holder.itemView.context,
                    "Feu servir el botó X per eliminar el producte",
                    Toast.LENGTH_SHORT
                ).show()
            }
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