package com.example.cafeteria.fragments

import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeteria.R
import com.example.cafeteria.adapters.CartAdapter
import com.example.cafeteria.viewmodels.SharedViewModel
import com.example.cafeteria.models.CartItem
import com.example.cafeteria.models.Product

class ComandaFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvTotal: TextView
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_comanda, container, false)
        recyclerView = view.findViewById(R.id.recyclerComanda)
        tvTotal = view.findViewById(R.id.tvTotal)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = CartAdapter(sharedViewModel)
        recyclerView.adapter = adapter

        // observar cambios del carrito
        sharedViewModel.cartMapLiveData.observe(viewLifecycleOwner) { map: Map<String, CartItem> ->
            adapter.submitMap(map)
        }

        sharedViewModel.cartTotal.observe(viewLifecycleOwner) { total ->
            tvTotal.text = "Total: %.2f€".format(total)
        }

        return view
    }
}