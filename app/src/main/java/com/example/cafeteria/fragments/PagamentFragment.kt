package com.example.cafeteria.fragments

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
import com.example.cafeteria.viewmodels.SharedViewModel
import com.example.cafeteria.adapters.CartAdapter

class PagamentFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var totalText: TextView
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pagament, container, false)

        recyclerView = view.findViewById(R.id.recyclerCart)
        totalText = view.findViewById(R.id.tvTotal)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CartAdapter(sharedViewModel)
        recyclerView.adapter = adapter

        // Observa los items del carrito
        sharedViewModel.cartItems.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            // Actualiza el total
            totalText.text = "Total: €${sharedViewModel.cartTotal.value ?: 0.0}"
        }

        // Observa los cambios en el total
        sharedViewModel.cartTotal.observe(viewLifecycleOwner) { total ->
            totalText.text = "Total: €${"%.2f".format(total)}"
        }

        return view
    }
}