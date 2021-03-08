package com.negahpay.sokkan.framework.net

import retrofit2.Response
import retrofit2.http.GET

interface SettingsService {
    @GET("/api/login")
    fun getApiKey(): Response<ReturnNet<Any>>
}