package com.negahpay.sokkan.framework.net

import com.negahpay.core.data.BillInquiryParameter
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BillInquiryParameterNet(
    @Json(name = "SenderUserName")
    val senderUsername: String,
    @Json(name = "SenderUniqueRequestId")
    val senderUniqueRequestId: String,
    @Json(name = "RequestedServiceID")
    val requestedServiceId: String,
    @Json(name = "BillID")
    val billId: String = "",
    @Json(name = "FixedLineNumber")
    val fixedLineNumber: String = "",
    @Json(name = "MobileNumber")
    val mobileNumber: String = "",
    @Json(name = "TraceNumber")
    val traceNumber: String
) {
    companion object {
        fun fromBillInquiryParameter(param: BillInquiryParameter) =
            BillInquiryParameterNet(
                param.senderUsername,
                param.senderUniqueRequestId,
                param.requestedServiceId,
                param.billId,
                param.fixedLineNumber,
                param.mobileNumber,
                param.traceNumber
            )
    }
}