package com.negahpay.sokkan.framework.net

import com.negahpay.sokkan.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ITokenService {
    @Headers("APIKey: ${BuildConfig.API_KEY}",
        "Authorization: ${BuildConfig.BASIC_AUTH}")
    @GET("/api/login")
    suspend fun getToken(): Response<ReturnNet<Any>>
}