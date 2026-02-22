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
import com.example.cafeteria.adapters.CartAdapter
import com.example.cafeteria.viewmodels.SharedViewModel

class PagamentFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvTotal: TextView
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_pagament, container, false)
        recyclerView = view.findViewById(R.id.recyclerCart)
        tvTotal = view.findViewById(R.id.tvTotal)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = CartAdapter(sharedViewModel)
        recyclerView.adapter = adapter

        // observar cambios del carrito
        sharedViewModel.cartMapLiveData.observe(viewLifecycleOwner) { map ->
            adapter.submitMap(map)
        }

        return view
    }
}