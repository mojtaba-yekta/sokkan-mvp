package com.negahpay.core.data

import java.util.*

data class BillInquiry(
    val id:Long,
    val fullName:String,
    val address:String,
    val amount:Int,
    val billId:String,
    val paymentId:String,
    val paymentDate:String
)