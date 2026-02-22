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


        btnPay.setOnClickListener {
            requireActivity()
                .findViewById<BottomNavigationView>(R.id.bottomNavigationView)
                .selectedItemId = R.id.pagamentFragment
        }

        val badge = view.findViewById<TextView>(R.id.tvBadge)

        sharedViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            badge.text = items.size.toString()
        }


        sharedViewModel.cartTotal.observe(viewLifecycleOwner) { total ->

            tvTotal.text = "Total: €%.2f".format(total)

            // ✨ Animación suave cuando cambia
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