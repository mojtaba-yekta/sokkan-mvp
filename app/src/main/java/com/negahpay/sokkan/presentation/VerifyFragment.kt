package com.negahpay.sokkan.presentation

import android.content.Context
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
import com.negahpay.sokkan.databinding.FragmentVerifyBinding
import com.negahpay.sokkan.framework.viewmodels.VerifyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyFragment : Fragment() {
    private var cellphone: String? = null
    private val viewModel: VerifyViewModel by viewModels()
    private var _binding: FragmentVerifyBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cellphone = it.getString("cellphone")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtCellphone.text = cellphone
        viewModel.cellphone = cellphone ?: ""
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
        binding.btnLogin.setOnClickListener {
            validate()
        }

        binding.txtPinEntry.setOnPinEnteredListener {
            if (it.length == 4)
                validate()
        }

        binding.txtCountDown.setOnClickListener {
            sendAgain()
        }
    }

    private fun observers() {
        viewModel.getValidateState().observe(viewLifecycleOwner) {
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
                        else
                            Navigation
                                .findNavController(binding.root)
                                .navigate(R.id.verify_to_dashboard)
                    }
                    endLoading()
                }
            }
        }
    }

    private fun validate() {
        hideKeyboard()
        viewModel.validateCode(binding.txtPinEntry.text.toString())
    }

    private fun sendAgain() {
        hideKeyboard()
        viewModel.sendAgain()

        binding.txtCountDown.isEnabled = false
        viewModel.getCountDown().observe(viewLifecycleOwner) {
            binding.txtCountDown.text = getString(R.string.count_down, it)
            if (it <= 0) {
                binding.txtCountDown.isEnabled = true
                binding.txtCountDown.text = getString(R.string.send_again)
            }
        }

        viewModel.getSendAgainLive().observe(viewLifecycleOwner){
            //do nothing
        }
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.txtCellphone.windowToken, 0)
    }

    private fun error(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun startLoading() {
        binding.progress.visibility = View.VISIBLE
        binding.btnLogin.visibility = View.GONE
    }

    private fun endLoading() {
        binding.progress.visibility = View.GONE
        binding.btnLogin.visibility = View.VISIBLE
    }

}