package com.negahpay.sokkan.framework.net

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IBillService {
    @POST("/api/v1/service/bill")
    suspend fun billInquiry(@Body billInquiryParameter: BillInquiryParameterNet)
            : Response<ReturnNet<BillInquiryNet>>
}