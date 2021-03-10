package com.negahpay.sokkan.framework.net

import com.negahpay.sokkan.BuildConfig

data class NetParams(
    val baseUrl: String = BuildConfig.BASE_URL,
    val apiKey: String = BuildConfig.API_KEY,
    val apiKeyHeader: String = "APIKey",
    val tokenHeader: String = "token",
    var token: String = ""
)