package com.negahpay.sokkan.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.negahpay.core.data.BillType
import com.negahpay.sokkan.R
import com.negahpay.sokkan.databinding.FragmentElectricityBillBinding
import com.negahpay.sokkan.databinding.FragmentIrancellBillBinding

class ElectricityBillFragment : Fragment() {
    private var _binding: FragmentElectricityBillBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentElectricityBillBinding.inflate(inflater, container, false)
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
                    putString("bill_type", BillType.Electricity.name)
                    putString("sender_unique_request_id","4")
                    putString("requested_service_id","4")
                    putString("trace_number","1000")
                }
                Navigation
                    .findNavController(binding.root)
                    .navigate(R.id.electricity_bill_to_bill_details, bundle)
            }
        }
    }
}