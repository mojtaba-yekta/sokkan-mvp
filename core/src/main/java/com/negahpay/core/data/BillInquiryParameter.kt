package com.negahpay.core.data

data class BillInquiryParameter(
    val senderUsername: String,
    val senderUniqueRequestId: String,
    val requestedServiceId: String,
    val billId: String = "",
    val fixedLineNumber: String = "",
    val mobileNumber: String = "",
    val traceNumber: String
)
