package com.negahpay.core.usecases.billinquiry

import com.negahpay.core.data.BillInquiryParameter
import com.negahpay.core.repository.BillInquiryRepository

class ReceiveBill(
    private val billInquiryRepository: BillInquiryRepository
) {
    suspend operator fun invoke(billInquiryParameter: BillInquiryParameter) =
        billInquiryRepository.receive(billInquiryParameter)
}