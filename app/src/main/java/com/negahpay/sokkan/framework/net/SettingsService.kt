package com.negahpay.sokkan.framework.net

import com.negahpay.sokkan.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface SettingsService {
    @Headers("APIKey: ${BuildConfig.API_KEY}")
    @GET("/api/login")
    fun getApiKey(): Response<ReturnNet<Any>>
}