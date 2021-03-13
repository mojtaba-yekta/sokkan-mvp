package com.negahpay.sokkan.framework.net

import com.negahpay.core.data.BillInquiry
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BillInquiryNet(
    @Json(name = "billInqueryId")
    val id: Long?,
    @Json(name = "fullName")
    val fullName: String?,
    @Json(name = "address")
    val address: String?,
    @Json(name = "amount")
    val amount: Int?,
    @Json(name = "billID")
    val billId: String?,
    @Json(name = "paymentID")
    val paymentId: String?,
    @Json(name = "paymentDate")
    val paymentDate: String?
) {
    fun toBillInquiry() =
        BillInquiry(
            id ?: 0,
            fullName ?: "",
            address ?: "",
            amount ?: 0,
            billId ?: "",
            paymentId ?: "",
            paymentDate ?: ""
        )
}