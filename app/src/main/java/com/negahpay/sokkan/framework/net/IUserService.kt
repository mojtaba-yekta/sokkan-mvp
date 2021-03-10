package com.negahpay.sokkan.framework.net

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IUserService {
    @POST("/api/v1/user/registermobile")
    suspend fun register(
        @Query("mobile") mobile: String
    ): Response<ReturnNet<Any>>

    @GET("/api/v1/user/mobileactivatecode")
    suspend fun mobileVerification(
        @Query("mobile") mobile: String
    ): Response<ReturnNet<Any>>

    @POST("/api/v1/user/activate")
    suspend fun verify(
        @Query("mobile") mobile: String,
        @Query("activationcode") verifyCode: String
    ): Response<ReturnNet<Any>>

}