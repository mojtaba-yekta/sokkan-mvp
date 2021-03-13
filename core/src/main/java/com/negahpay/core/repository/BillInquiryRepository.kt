package com.negahpay.core.repository

import com.negahpay.core.data.BillInquiryParameter

class BillInquiryRepository(
    private val billInquiryDataSource: BillInquiryDataSource
) {
    suspend fun receive(billInquiryParameter: BillInquiryParameter) =
        billInquiryDataSource.receive(billInquiryParameter)
}