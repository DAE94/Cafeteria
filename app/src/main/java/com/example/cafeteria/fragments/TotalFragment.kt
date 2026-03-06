package com.example.cafeteria.fragments

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cafeteria.R
import com.example.cafeteria.viewmodels.SharedViewModel
import androidx.navigation.fragment.findNavController
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView

class TotalFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_total, container, false)

        val tvTotal = view.findViewById<TextView>(R.id.tvTotalHeader)

        val btnPay = view.findViewById<Button>(R.id.btnPay)

        //Navega al fragment Pagament
        btnPay.setOnClickListener {
            findNavController().navigate(R.id.pagamentFragment)
        }

        val badge = view.findViewById<TextView>(R.id.tvBadge)

        sharedViewModel.cartMapLiveData.observe(viewLifecycleOwner) { cartMap ->
            val totalItems = cartMap.values.sumOf{ it.quantity} // suma de todas las cantidades
            badge.text = totalItems.toString()
        }


        sharedViewModel.cartTotal.observe(viewLifecycleOwner) { total ->

            tvTotal.text = "Total: %.2f€".format(total)

            // Animació quan canvia
            view.animate()
                .scaleX(1.05f)
                .scaleY(1.05f)
                .setDuration(120)
                .withEndAction {
                    view.animate().scaleX(1f).scaleY(1f).duration = 120
                }
        }

        return view
    }
}