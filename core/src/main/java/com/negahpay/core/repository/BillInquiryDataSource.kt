package com.negahpay.core.repository

import com.negahpay.core.data.BillInquiry
import com.negahpay.core.data.BillInquiryParameter
import com.negahpay.core.utils.Resource

interface BillInquiryDataSource {
    suspend fun receive(param: BillInquiryParameter): Resource<BillInquiry>
}