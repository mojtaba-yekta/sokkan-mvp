package com.negahpay.sokkan.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.negahpay.core.data.BillType
import com.negahpay.core.utils.Resource
import com.negahpay.sokkan.R
import com.negahpay.sokkan.databinding.FragmentBillDetailsBinding
import com.negahpay.sokkan.framework.viewmodels.BillDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BillDetailsFragment : Fragment() {
    private var billId: String? = null
    private var billType: BillType? = null
    private var senderUniqueRequestId: String? = null
    private var requestedServiceId: String? = null
    private var traceNumber: String? = null

    private val viewModel: BillDetailsViewModel by viewModels()
    private var _binding: FragmentBillDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            billId = it.getString("bill_id")
            billType = BillType.valueOf(it.getString("bill_type", "Water"))
            senderUniqueRequestId = it.getString("sender_unique_request_id")
            requestedServiceId = it.getString("requested_service_id")
            traceNumber = it.getString("trace_number")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBillDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.billId = billId ?: ""
        viewModel.senderUniqueRequestId = senderUniqueRequestId ?: ""
        viewModel.requestedServiceId = requestedServiceId ?: ""
        viewModel.traceNumber = traceNumber ?: ""
        observers()
        initView()
    }

    private fun initView() {
        when (billType) {
            BillType.Electricity -> {
                context?.let {
                    binding.billIcon.setImageDrawable(
                        ContextCompat.getDrawable(it, R.drawable.draw_tavanir)
                    )
                }
            }
            BillType.Irancell -> {
                context?.let {
                    binding.billIcon.setImageDrawable(
                        ContextCompat.getDrawable(it, R.drawable.draw_irancell)
                    )
                }
            }
            BillType.Phone -> {
                context?.let {
                    binding.billIcon.setImageDrawable(
                        ContextCompat.getDrawable(it, R.drawable.draw_ict)
                    )
                }
            }
            BillType.Water -> {
                context?.let {
                    binding.billIcon.setImageDrawable(
                        ContextCompat.getDrawable(it, R.drawable.draw_water_org)
                    )
                }
            }
        }
    }

    private fun observers() {
        viewModel.getData().observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.ERROR -> {
                    error(it.message ?: getString(R.string.error_get_api))
                    endLoading()
                    Navigation.findNavController(binding.root).popBackStack()
                }
                Resource.Status.LOADING -> {
                    startLoading()
                }
                Resource.Status.SUCCESS -> {
                    it.data?.let { b ->
                        binding.txtBillId.text = b.billId
                        binding.txtDueDate.text = b.paymentDate
                        binding.txtPayAmount.text = "${b.amount}"
                        binding.txtPayId.text = b.paymentId
                    }

                    endLoading()
                }
            }
        })
    }

    private fun error(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun startLoading() {
        binding.pnlProgress.visibility = View.VISIBLE
        binding.pnlDetails.visibility = View.GONE
    }

    private fun endLoading() {
        binding.pnlProgress.visibility = View.GONE
        binding.pnlDetails.visibility = View.VISIBLE
    }
}