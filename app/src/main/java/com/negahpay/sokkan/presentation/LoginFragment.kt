package com.negahpay.sokkan.presentation

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.negahpay.core.utils.Resource
import com.negahpay.sokkan.R
import com.negahpay.sokkan.databinding.FragmentLoginBinding
import com.negahpay.sokkan.framework.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
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
        binding.btnSendVerifyCode.setOnClickListener {
            hideKeyboard()
            viewModel.sendVerifyCode(binding.txtCellphone.text.toString())
        }
    }

    private fun observers() {
        viewModel.getVerifyStat().observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.ERROR -> {
                    error(it.message ?: getString(R.string.error_get_api))
                    endLoading()
                }
                Resource.Status.LOADING -> {
                    startLoading()
                }
                Resource.Status.SUCCESS -> {
                    it.data.let { c ->
                        if (c.isNullOrEmpty())
                            error("null cellphone")
                        else {
                            val bundle = Bundle().apply {
                                putString("cellphone", it.data)
                            }
                            Navigation
                                .findNavController(binding.root)
                                .navigate(R.id.login_to_verify, bundle)
                        }
                    }
                    endLoading()
                }
            }
        }
    }

    private fun error(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun startLoading() {
        binding.progress.visibility = View.VISIBLE
        binding.btnSendVerifyCode.visibility = View.GONE
    }

    private fun endLoading() {
        binding.progress.visibility = View.GONE
        binding.btnSendVerifyCode.visibility = View.VISIBLE
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.txtCellphone.windowToken, 0)
    }
}