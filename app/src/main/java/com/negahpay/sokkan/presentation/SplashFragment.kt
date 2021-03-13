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
import com.negahpay.core.utils.Resource
import com.negahpay.sokkan.R
import com.negahpay.sokkan.databinding.FragmentSplashBinding
import com.negahpay.sokkan.framework.viewmodels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private val viewModel: SplashViewModel by viewModels()
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }

    private fun observers() {
        viewModel.getToken().observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.ERROR -> {
                    error()
                }
                Resource.Status.LOADING -> {
                }
                Resource.Status.SUCCESS -> {
                    when (viewModel.navType()) {
                        SplashViewModel.NavType.LOGIN ->
                            Navigation
                                .findNavController(binding.root)
                                .navigate(R.id.splash_to_login)
                        SplashViewModel.NavType.DASHBOARD ->
                            Navigation
                                .findNavController(binding.root)
                                .navigate(R.id.splash_to_dashboard)
                    }
                }
            }
        })
    }

    private fun error() {
        Snackbar.make(binding.root, R.string.error_get_api, Snackbar.LENGTH_LONG)
            .setAction(R.string.retry_action) {
                viewModel.receiveToken()
            }
            .show()
    }
}