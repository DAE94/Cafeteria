package com.example.cafeteria.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeteria.models.Product
import com.example.cafeteria.R
import com.example.cafeteria.viewmodels.SharedViewModel
import com.example.cafeteria.adapters.ProductAdapter
import com.example.cafeteria.models.getProductsByCategory


class ReposteriaFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reposteria, container, false)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerReposteria)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        adapter = ProductAdapter(emptyList(), sharedViewModel)
        recycler.adapter = adapter
        sharedViewModel.cartItems.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }

        getProductsByCategory("reposteria") { list ->
            adapter.replaceData(list)
        }

        return view
    }
}