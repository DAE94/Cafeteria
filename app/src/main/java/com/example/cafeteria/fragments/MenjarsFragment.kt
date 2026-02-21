package com.example.cafeteria.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeteria.models.Product
import com.example.cafeteria.R
import com.example.cafeteria.adapters.ProductAdapter
import com.example.cafeteria.viewmodels.SharedViewModel

class MenjarsFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter

    private val menjarsList = listOf<Product>(
        Product("Bocadillo", 3.50),
        Product("Hamburguesa", 5.00),
        Product("Ensalada", 4.00),
        Product("Pizza", 6.50),
        Product("Pasta", 5.50)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menjars, container, false)
        recyclerView = view.findViewById(R.id.recyclerMenjars)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ProductAdapter(menjarsList, sharedViewModel)
        recyclerView.adapter = adapter
        return view
    }
}