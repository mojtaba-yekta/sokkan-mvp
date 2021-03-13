package com.negahpay.sokkan.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.negahpay.sokkan.R
import com.negahpay.sokkan.databinding.FragmentDashboardBinding
import com.negahpay.sokkan.databinding.FragmentWaterBillBinding

class WaterBillFragment : Fragment() {
    private var _binding: FragmentWaterBillBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWaterBillBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()
    }

    private fun listeners() {
        binding.btnBillQuery.setOnClickListener {
            val billId = binding.txtBillId.text.toString()
            if (billId.isNotEmpty()) {
                val bundle = Bundle().apply {
                    putString("bill_id", billId)
                }
                Navigation
                    .findNavController(binding.root)
                    .navigate(R.id.water_bill_to_bill_details, bundle)
            }
        }
    }
}