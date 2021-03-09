package com.negahpay.sokkan.framework.net

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @POST("/api/v1/user/registermobile")
    fun register(@Query("mobile") mobile: String): Response<ReturnNet<Any>>
}