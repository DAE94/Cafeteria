package com.example.cafeteria.fragments

import android.os.Build.VERSION_CODES.P
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
import com.example.cafeteria.viewmodels.SharedViewModel
import com.example.cafeteria.adapters.ProductAdapter


class PostresFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter

    private val postresList = listOf<Product>(
        Product("Tarta de queso", 3.00),
        Product("Brownie", 2.50),
        Product("Helado", 2.00)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_postres, container, false)
        recyclerView = view.findViewById(R.id.recyclerPostres)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ProductAdapter(postresList, sharedViewModel)
        recyclerView.adapter = adapter
        return view
    }
}