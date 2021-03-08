package com.negahpay.sokkan.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.negahpay.sokkan.R
import com.negahpay.sokkan.databinding.FragmentSplashBinding
import com.negahpay.sokkan.framework.SplashViewModel

class SplashFragment : Fragment() {
    private val viewModel: SplashViewModel by viewModels()
    private var _binding: FragmentSplashBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()
    }

    private fun observers() {
        viewModel.hasApiKey.observe(viewLifecycleOwner, {
            if (it) {
                if (viewModel.currentUser.isLogin) {
                    Navigation.findNavController(binding.root).navigate(R.id.splash_to_dashboard)
                } else {
                    Navigation.findNavController(binding.root).navigate(R.id.splash_to_login)
                }
            }
        })

        viewModel.errorGettingApiKey.observe(viewLifecycleOwner, {
            if (it) {
                showError()
            }
        })
    }

    private fun showError() {
        Snackbar.make(binding.root,R.string.error_get_api,Snackbar.LENGTH_LONG)
            .setAction(R.string.retry){
                viewModel.receiveApiKey()
            }
            .show()
    }
}