package com.example.cafeteria.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeteria.R
import com.example.cafeteria.viewmodels.SharedViewModel
import com.example.cafeteria.adapters.ProductAdapter
import com.example.cafeteria.models.Product


class BegudesFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter

    private val begudesList = listOf<Product>(
        Product("Coca-Cola", 1.50),
        Product("Fanta", 1.50),
        Product("Agua", 1.00),
        Product("Zumo de naranja", 2.00),
        Product("Cerveza", 2.50),
        Product("Vino tinto", 3.00),
        Product("Vino blanco", 3.00),
        Product("Café", 1.50),
        Product("Té", 1.50),
        Product("Infusión", 1.50)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_begudes, container, false)
        recyclerView = view.findViewById(R.id.recyclerBegudes)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ProductAdapter(begudesList, sharedViewModel)
        recyclerView.adapter = adapter
        return view
    }
}