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
import com.example.cafeteria.R
import com.example.cafeteria.viewmodels.SharedViewModel
import com.example.cafeteria.adapters.ProductAdapter
import com.example.cafeteria.models.Product
import com.example.cafeteria.models.getProductsByCategory
import com.google.firebase.firestore.FirebaseFirestore


class BegudesFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_begudes, container, false)


        sharedViewModel = activityViewModels<SharedViewModel>().value

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerBegudes)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        adapter = ProductAdapter(emptyList(), sharedViewModel)
        recycler.adapter = adapter


        sharedViewModel.cartMapLiveData.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }

        // instancia BBDD per observar stock en temps real
        FirebaseFirestore.getInstance()
            .collection("productes")
            .whereEqualTo("categoria", "beguda")
            .addSnapshotListener { snapshot, _ ->
                val products = snapshot?.toObjects(Product::class.java) ?: emptyList()
                products.forEach { sharedViewModel.updateProduct(it) }
                adapter.replaceData(products)
            }

        return view
    }
}