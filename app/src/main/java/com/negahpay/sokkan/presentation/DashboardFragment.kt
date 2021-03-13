package com.negahpay.sokkan.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.negahpay.sokkan.R
import com.negahpay.sokkan.databinding.FragmentDashboardBinding
import com.negahpay.sokkan.framework.viewmodels.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private val viewModel: DashboardViewModel by viewModels()
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()
        listeners()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }

    private fun listeners() {
        binding.btnLogout.setOnClickListener { logout() }
        binding.pnlWater.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.dashboard_to_water_bill)
        }
    }

    private fun observers() {
        viewModel.getUser().observe(viewLifecycleOwner) {
            binding.txtCellphone.text = it.cellphone
        }
    }

    private fun logout() {
        viewModel.logout()
        viewModel.getIsLogout().observe(viewLifecycleOwner) {
            if (it)
                Navigation.findNavController(binding.root).navigate(R.id.dashboard_to_login)
        }
    }

}