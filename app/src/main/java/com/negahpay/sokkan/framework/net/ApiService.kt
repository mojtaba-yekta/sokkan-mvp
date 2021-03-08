package com.negahpay.sokkan.framework.net

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/api/login")
    fun getApiKey(): Call<ReturnNet<Any>>

}